package br.com.douglas444.patternsampling.lowlevel;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;
import br.com.douglas444.patternsampling.common.Oracle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KMostOrLessInformative implements LowLevelStrategy {

    private int k;
    private int dimensionality;

    public KMostOrLessInformative(int k) {
        this.k = k;
        this.dimensionality = 1;
    }

    @Override
    public boolean isNovelty(final ClusterSummary targetClusterSummary, final List<Sample> samples,
                             final List<ClusterSummary> summaries, final Set<Integer> knownLabels) {

        if (samples.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<Sample> samplesSortedByInformationGain = new ArrayList<>(samples);

        samplesSortedByInformationGain
                .sort(Comparator.comparing(sample -> calculateInformationGain(
                        sample,
                        summaries,
                        knownLabels,
                        this.dimensionality)));

        final List<Sample> kLessInformative = samplesSortedByInformationGain.subList(0, this.k);

        return Oracle.isNovelty(kLessInformative, knownLabels);

    }

    @Override
    public boolean isKnown(final ClusterSummary targetClusterSummary, final List<Sample> samples,
                           final List<ClusterSummary> summaries, final Set<Integer> knownLabels) {

        if (samples.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final List<Sample> samplesReverseSortedByInformationGain = new ArrayList<>(samples);

        samplesReverseSortedByInformationGain
                .sort(Comparator.comparing(sample -> 1 - calculateInformationGain(
                        sample,
                        summaries,
                        knownLabels,
                        this.dimensionality)));

        final List<Sample> kMostInformative = samplesReverseSortedByInformationGain.subList(0, this.k);

        return !Oracle.isNovelty(kMostInformative, knownLabels);
    }

    private static double calculateInformationGain(final Sample target, final List<ClusterSummary> summaries,
                                                   final Set<Integer> knownLabels, final int dimensionality) {


        final List<Sample> knownConceptsCentroids = summaries
                .stream()
                .map(ClusterSummary::calculateCentroid)
                .collect(Collectors.toList());

        final List<Sample> closestCentroids = new ArrayList<>();

        knownLabels.forEach((knownLabel) -> {

            final Sample closestCentroid = knownConceptsCentroids
                    .stream()
                    .filter(centroid -> centroid.getY().equals(knownLabel))
                    .min(Comparator.comparing((Sample sample) -> sample.distance(target)))
                    .orElse(knownConceptsCentroids.get(0));

            closestCentroids.add(closestCentroid);

        });


        final double n = Math.pow(1.0 / closestCentroids
                .stream()
                .map(centroid -> centroid.distance(target))
                .min(Double::compare)
                .orElse(0.0), dimensionality);

        if (Double.isInfinite(n)) {
            return 0;
        }

        if (closestCentroids.size() == 1) {
            return 1;
        }

        final double d = closestCentroids
                .stream()
                .map(centroid -> Math.pow(1.0 / centroid.distance(target), dimensionality))
                .reduce(0.0, Double::sum);

        final double probability = n / d;
        final double error = 1 - probability;
        final double maxError = 1 - 1 / (double) closestCentroids.size();
        return error / maxError;
    }

    public int getK() {
        return k;
    }

    public KMostOrLessInformative setK(int k) {
        this.k = k;
        return this;
    }
}
