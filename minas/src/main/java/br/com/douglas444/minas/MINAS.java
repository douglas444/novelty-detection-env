package br.com.douglas444.minas;

import br.com.douglas444.minas.heater.Heater;
import br.com.douglas444.minas.interceptor.MINASInterceptor;
import br.com.douglas444.mltk.clustering.kmeans.KMeansPlusPlus;
import br.com.douglas444.mltk.datastructure.*;
import br.com.douglas444.patternsampling.common.ConceptClassificationContext;
import br.com.douglas444.patternsampling.common.WarmUpContext;
import br.com.douglas444.patternsampling.types.ConceptCategory;

import java.util.*;
import java.util.stream.Collectors;

public class MINAS {

    private long timestamp;
    private int noveltyCount;
    private boolean warmed;
    private int heaterCapacity;

    private final DecisionModel decisionModel;
    private final List<Sample> temporaryMemory;
    private final DecisionModel sleepMemory ;
    private final DynamicConfusionMatrix confusionMatrix;

    private final int temporaryMemoryMaxSize;
    private final int minimumClusterSize;
    private final int windowSize;
    private final int microClusterLifespan;
    private final int sampleLifespan;
    private final Random random;
    private final int noveltyDetectionNumberOfClusters;
    private final Heater heater;

    private final MINASInterceptor interceptor;

    public MINAS(int temporaryMemoryMaxSize,
                 int minimumClusterSize,
                 int windowSize,
                 int microClusterLifespan,
                 int sampleLifespan,
                 int heaterCapacity,
                 boolean incrementallyUpdateDecisionModel,
                 int heaterInitialBufferSize,
                 int heaterNumberOfClustersPerLabel,
                 int noveltyDetectionNumberOfClusters,
                 long randomGeneratorSeed,
                 MINASInterceptor interceptor) {

        this.timestamp = 1;
        this.noveltyCount = 0;
        this.warmed = false;
        this.temporaryMemory = new ArrayList<>();

        this.heaterCapacity = heaterCapacity;
        this.temporaryMemoryMaxSize = temporaryMemoryMaxSize;
        this.minimumClusterSize = minimumClusterSize;
        this.windowSize = windowSize;
        this.microClusterLifespan = microClusterLifespan;
        this.sampleLifespan = sampleLifespan;
        this.noveltyDetectionNumberOfClusters = noveltyDetectionNumberOfClusters;
        this.random = new Random(randomGeneratorSeed);

        this.interceptor = (interceptor == null) ? new MINASInterceptor() : interceptor;
        this.heater = new Heater(heaterInitialBufferSize, heaterNumberOfClustersPerLabel, this.random);
        this.decisionModel = new DecisionModel(incrementallyUpdateDecisionModel, this.interceptor);
        this.sleepMemory = new DecisionModel(incrementallyUpdateDecisionModel, this.interceptor);
        this.confusionMatrix = new DynamicConfusionMatrix();

    }

    private void warmUp(final Sample sample) {

        assert !warmed;

        if (!this.confusionMatrix.isLabelKnown(sample.getY())) {
            this.confusionMatrix.addKnownLabel(sample.getY());
        }

        this.heater.process(sample);

        if (this.heaterCapacity > 1) {
            this.heaterCapacity--;
        } else {

            this.warmed = true;

            final List<MicroCluster> microClusters = this.heater.getResult().stream()
                    .filter(microCluster -> microCluster.getN() >= 3)
                    .collect(Collectors.toCollection(ArrayList::new));

            microClusters.forEach(microCluster -> microCluster.setTimestamp(this.timestamp));
            this.decisionModel.merge(microClusters);


            this.interceptor.WARM_UP_AL_FRAMEWORK.with(
                    new WarmUpContext()
                            .setPseudoPoints(
                                    this.decisionModel.getMicroClusters()
                                            .stream()
                                            .map(MicroCluster::calculateCentroid)
                                            .map(PseudoPoint::new)
                                            .collect(Collectors.toList())))
                    .executeOrDefault(() -> {});
        }

    }



    private void detectNoveltyAndUpdate() {

        final List<Cluster> clusters = KMeansPlusPlus
                .execute(this.temporaryMemory, this.noveltyDetectionNumberOfClusters, this.random)
                .stream()
                .filter(cluster -> cluster.getSize() >= this.minimumClusterSize)
                .sorted(Comparator.comparing(cluster -> cluster.getMostRecentSample().getT()))
                .collect(Collectors.toList());

        for (Cluster cluster : clusters) {

            if (this.decisionModel.calculateSilhouette(cluster) <= 0) {
                continue;
            }

            this.temporaryMemory.removeAll(cluster.getSamples());

            final MicroCluster targetMicroCluster = new MicroCluster(cluster, cluster.getMostRecentSample().getT());

            final Set<Integer> knownLabels = new HashSet<>();

            knownLabels.addAll(this.decisionModel.getMicroClusters().stream()
                    .filter(microCluster -> microCluster.getMicroClusterCategory() != MicroClusterCategory.NOVELTY)
                    .map(MicroCluster::getLabel)
                    .collect(Collectors.toSet()));

            knownLabels.addAll(this.sleepMemory.getMicroClusters().stream()
                    .filter(microCluster -> microCluster.getMicroClusterCategory() != MicroClusterCategory.NOVELTY)
                    .map(MicroCluster::getLabel)
                    .collect(Collectors.toSet()));

            final List<ClusterSummary> knownClusterSummaries = this.decisionModel
                    .getMicroClusters()
                    .stream()
                    .filter(microCluster -> microCluster.getMicroClusterCategory() != MicroClusterCategory.NOVELTY)
                    .map(MicroCluster::toClusterFeature)
                    .collect(Collectors.toList());

            knownClusterSummaries.addAll(this.sleepMemory.getMicroClusters()
                    .stream()
                    .filter(microCluster -> microCluster.getMicroClusterCategory() != MicroClusterCategory.NOVELTY)
                    .map(MicroCluster::toClusterFeature)
                    .collect(Collectors.toList()));

            final ConceptClassificationContext context = new ConceptClassificationContext()
                    .setTargetClusterSummary(targetMicroCluster.toClusterFeature())
                    .setTargetSamples(cluster.getSamples())
                    .setKnownClusterSummaries(knownClusterSummaries)
                    .setKnownLabels(knownLabels);

            this.decisionModel.classify(targetMicroCluster).ifExplainedOrElse((closest) -> {

                if (closest.getMicroClusterCategory() == MicroClusterCategory.KNOWN) {
                    context.setDecision(ConceptCategory.KNOWN);
                } else {
                    context.setDecision(ConceptCategory.NOVELTY);
                }

                //if (closest.getMicroClusterCategory() == MicroClusterCategory.KNOWN) {
                    //context.setDecision(ConceptCategory.KNOWN);
                    this.interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.with(context).executeOrDefault(() -> {});
                //}

                this.addExtension(targetMicroCluster, closest);

            }, () -> {

                this.sleepMemory.classify(targetMicroCluster).ifExplainedOrElse((closest) -> {

                    if (closest.getMicroClusterCategory() == MicroClusterCategory.KNOWN) {
                        context.setDecision(ConceptCategory.KNOWN);
                    } else {
                        context.setDecision(ConceptCategory.NOVELTY);
                    }

                    //if (closest.getMicroClusterCategory() == MicroClusterCategory.KNOWN) {
                        //context.setDecision(ConceptCategory.KNOWN);
                        this.interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.with(context).executeOrDefault(() -> {});
                    //}

                    this.awake(closest);
                    this.addExtension(targetMicroCluster, closest);


                }, (optionalClosest) -> {

                    context.setDecision(ConceptCategory.NOVELTY);
                    this.interceptor.NOVELTY_DETECTION_AL_FRAMEWORK.with(context).executeOrDefault(() -> {});
                    this.addNovelty(targetMicroCluster);

                });
            });

            for (Sample sample : cluster.getSamples()) {
                this.confusionMatrix.updatedDelayed(sample.getY(), targetMicroCluster.getLabel(),
                        targetMicroCluster.getMicroClusterCategory() == MicroClusterCategory.NOVELTY);
            }

        }
    }

    public Classification process(final Sample sample) {

        if (!this.warmed) {
            this.warmUp(sample);
            return new Classification(null, false);
        }

        sample.setT(this.timestamp);

        final Classification classification = this.decisionModel.classify(sample);

        classification.ifExplainedOrElse((closestMicroCluster) -> {

            this.confusionMatrix.addPrediction(sample.getY(), closestMicroCluster.getLabel(),
                    closestMicroCluster.getMicroClusterCategory() == MicroClusterCategory.NOVELTY);

        }, () -> {
            this.temporaryMemory.add(sample);
            this.confusionMatrix.addUnknown(sample.getY());
            if (this.temporaryMemory.size() >= this.temporaryMemoryMaxSize) {
                this.detectNoveltyAndUpdate();
            }
        });

        if (this.timestamp % this.windowSize == 0) {
            final List<MicroCluster> inactiveMicroClusters = this.decisionModel
                    .extractInactiveMicroClusters(this.timestamp, microClusterLifespan);
            this.sleepMemory.merge(inactiveMicroClusters);
            this.temporaryMemory.removeIf(p -> p.getT() < this.timestamp - this.sampleLifespan);
        }

        ++this.timestamp;

        return classification;

    }

    private void awake(MicroCluster microCluster) {
        this.sleepMemory.remove(microCluster);
        this.decisionModel.merge(microCluster);
    }

    private void addNovelty(MicroCluster microCluster) {
        microCluster.setMicroClusterCategory(MicroClusterCategory.NOVELTY);
        microCluster.setLabel(this.noveltyCount++);
        this.decisionModel.merge(microCluster);
    }

    private void addExtension(MicroCluster microCluster, MicroCluster closestMicroCluster) {
        microCluster.setMicroClusterCategory(closestMicroCluster.getMicroClusterCategory());
        microCluster.setLabel(closestMicroCluster.getLabel());
        this.decisionModel.merge(microCluster);
    }

    public long getTimestamp() {
        return timestamp - 1;
    }

    public DynamicConfusionMatrix getConfusionMatrix() {
        return confusionMatrix;
    }

    public double calculateCER() {
        return this.confusionMatrix.measureCER();
    }

    public double calculateUnkR() {
        return this.confusionMatrix.measureUnkR();
    }

    public int getNoveltyCount() {
        return noveltyCount;
    }

}