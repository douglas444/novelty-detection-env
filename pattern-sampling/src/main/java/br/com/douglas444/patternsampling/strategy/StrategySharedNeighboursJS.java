package br.com.douglas444.patternsampling.strategy;

import br.com.douglas444.mltk.datastructure.ClusterFeature;
import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;
import br.com.douglas444.patternsampling.strategy.common.BayesianErrorEstimation;
import br.com.douglas444.patternsampling.strategy.common.JamesSteinEstimator;
import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.ConceptCategory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StrategySharedNeighboursJS extends Strategy {

    private double threshold;
    private double kFactor;

    public StrategySharedNeighboursJS(double threshold, double kFactor) {
        this.threshold = threshold;
        this.kFactor = kFactor;
    }

    @Override
    public Double getParameter1() {
        return threshold;
    }

    @Override
    public Double getParameter2() {
        return kFactor;
    }

    @Override
    protected ConceptCategory predictConceptCategory(final ConceptCategory frameworkDecision,
                                                     final ClusterSummary targetClusterSummary,
                                                     final List<ClusterSummary> knownClusterSummaries,
                                                     final Set<Integer> knownLabels) {

        if (!(targetClusterSummary instanceof ClusterFeature)) {
            throw new IllegalArgumentException();
        }

        if (!knownClusterSummaries.isEmpty() && !(knownClusterSummaries.get(0) instanceof ClusterFeature)) {
            throw new IllegalArgumentException();
        }

        if (knownClusterSummaries.isEmpty()) {
            return ConceptCategory.NOVELTY;
        }

        final ClusterFeature[] knownClusterFeatures = knownClusterSummaries
                .stream()
                .map(clusterSummary -> (ClusterFeature) clusterSummary)
                .toArray(ClusterFeature[]::new);

        final ClusterFeature targetClusterFeature = (ClusterFeature) targetClusterSummary;
        final Sample targetCentroid = targetClusterFeature.calculateCentroid();

        final Sample[] centroids = Arrays.stream(knownClusterFeatures)
                .map(ClusterFeature::calculateCentroid)
                .toArray(Sample[]::new);

        final double[] shrinkageFactors = JamesSteinEstimator.calculateShrinkageFactors(
                targetCentroid.getX(), knownClusterFeatures, centroids);

        for (int i = 0; i < centroids.length; ++i) {
            final Sample difference;
            difference = centroids[i].difference(targetCentroid);
            if (frameworkDecision == ConceptCategory.NOVELTY) {
                difference.multiply(shrinkageFactors[i]);
            } else {
                difference.multiply(2 - shrinkageFactors[i]);
            }
            difference.sum(targetCentroid);
            centroids[i] = difference;
        }

        final double bayesianErrorEstimation;

        if (knownClusterSummaries.isEmpty()) {
            bayesianErrorEstimation = 1;
        } else {
            bayesianErrorEstimation = BayesianErrorEstimation.estimateBayesianErrorNeighbours(targetClusterSummary,
                    knownClusterSummaries, knownLabels);
        }

        if (bayesianErrorEstimation > this.threshold) {
            return ConceptCategory.NOVELTY;
        } else {
            return ConceptCategory.KNOWN;
        }

    }


}
