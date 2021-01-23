package br.com.douglas444.patternsampling.strategy;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.patternsampling.strategy.common.BayesianErrorEstimation;
import br.com.douglas444.patternsampling.strategy.common.Strategy;
import br.com.douglas444.patternsampling.strategy.common.Valuable;
import br.com.douglas444.patternsampling.types.ConceptCategory;

import java.util.List;
import java.util.Set;

public class StrategyWeightedEuclideanDistance extends Strategy implements Valuable {

    private final double threshold;

    public StrategyWeightedEuclideanDistance(final double threshold) {
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

        double bayesianErrorEstimation = getValue(targetClusterSummary, knownClusterSummaries, knownLabels);

        if (bayesianErrorEstimation > this.threshold) {
            return ConceptCategory.NOVELTY;
        } else {
            return ConceptCategory.KNOWN;
        }

    }

    @Override
    public double getValue(final ClusterSummary targetClusterSummary,
                           final List<ClusterSummary> knownClusterSummaries,
                           final Set<Integer> knownLabels) {
        double bayesianErrorEstimation;
        if (knownClusterSummaries.isEmpty()) {
            bayesianErrorEstimation = 1;
        } else {
            bayesianErrorEstimation = BayesianErrorEstimation.weightedDistanceProbability(
                    targetClusterSummary,
                    knownClusterSummaries,
                    knownLabels);
        }
        return bayesianErrorEstimation;
    }

}
