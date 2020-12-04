package br.com.douglas444.patternsampling.strategy;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;
import br.com.douglas444.patternsampling.strategy.common.BayesianErrorEstimation;
import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.types.ConceptCategory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StrategyEuclideanDistance extends Strategy {

    private final double threshold;

    public StrategyEuclideanDistance(final double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Double getParameter1() {
        return threshold;
    }

    @Override
    public Double getParameter2() {
        return null;
    }

    @Override
    protected ConceptCategory predictConceptCategory(final ConceptCategory frameworkDecision,
                                                     final ClusterSummary targetClusterSummary,
                                                     final List<ClusterSummary> knownClusterSummaries,
                                                     final Set<Integer> knownLabels) {

        final Sample targetConceptCentroid = targetClusterSummary.calculateCentroid();

        final List<Sample> knownConceptsCentroids = knownClusterSummaries
                .stream()
                .map(ClusterSummary::calculateCentroid)
                .collect(Collectors.toList());

        double bayesianErrorEstimation;
        if (knownConceptsCentroids.isEmpty()) {
            bayesianErrorEstimation = 1;
        } else {
            bayesianErrorEstimation = BayesianErrorEstimation
                    .estimateBayesianErrorDistanceBased(targetConceptCentroid, knownConceptsCentroids, knownLabels);
        }

        if (bayesianErrorEstimation > this.threshold) {
            return ConceptCategory.NOVELTY;
        } else {
            return ConceptCategory.KNOWN;
        }

    }

}
