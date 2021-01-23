package br.com.douglas444.patternsampling.strategy.common;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;
import br.com.douglas444.patternsampling.common.ConceptClassificationContext;
import br.com.douglas444.patternsampling.common.Oracle;
import br.com.douglas444.patternsampling.lowlevel.LowLevelStrategy;
import br.com.douglas444.patternsampling.types.ConceptCategory;
import br.com.douglas444.patternsampling.types.DecisionCategory;
import br.com.douglas444.patternsampling.types.InterceptionResult;

import java.util.List;
import java.util.Set;

public abstract class Strategy {

    private LowLevelStrategy lowLevelStrategy;
    public abstract Double getParameter1();
    public abstract Double getParameter2();

    public InterceptionResult intercept(final ConceptClassificationContext context) {

        if (context.getTargetSamples().isEmpty()) {
            throw new IllegalArgumentException();
        }

        final ConceptCategory realCategory;
        if (Oracle.isNovelty(context.getTargetSamples(), context.getKnownLabels())) {
            realCategory = ConceptCategory.NOVELTY;
        } else {
            realCategory = ConceptCategory.KNOWN;
        }

        final ConceptCategory indicatorConceptCategoryPrediction = predictConceptCategory(
                context.getDecision(),
                context.getTargetClusterSummary(),
                context.getKnownClusterSummaries(),
                context.getKnownLabels());

        final DecisionCategory indicatorDecisionCategoryPrediction;

        if (indicatorConceptCategoryPrediction == context.getDecision()) {
            indicatorDecisionCategoryPrediction = DecisionCategory.RELIABLE;
        } else {
            indicatorDecisionCategoryPrediction = DecisionCategory.RISKY;
        }

        final ConceptCategory lowLevelStrategyPrediction;

        if (this.lowLevelStrategy != null && indicatorDecisionCategoryPrediction == DecisionCategory.RISKY) {

            if (context.getDecision() == ConceptCategory.KNOWN) {

                boolean validatedAsKnown = lowLevelStrategy.isKnown(
                        context.getTargetClusterSummary(),
                        context.getTargetSamples(),
                        context.getKnownClusterSummaries(),
                        context.getKnownLabels());

                if (validatedAsKnown) {
                    lowLevelStrategyPrediction = ConceptCategory.KNOWN;
                } else {
                    lowLevelStrategyPrediction = ConceptCategory.NOVELTY;
                }

            } else {


                boolean validatedAsNovelty = lowLevelStrategy.isNovelty(
                        context.getTargetClusterSummary(),
                        context.getTargetSamples(),
                        context.getKnownClusterSummaries(),
                        context.getKnownLabels());

                if (validatedAsNovelty) {
                    lowLevelStrategyPrediction = ConceptCategory.NOVELTY;
                } else {
                    lowLevelStrategyPrediction = ConceptCategory.KNOWN;
                }

            }

        } else {
            lowLevelStrategyPrediction = null;
        }

        return new InterceptionResult(realCategory, context.getDecision(), indicatorConceptCategoryPrediction,
                indicatorDecisionCategoryPrediction, lowLevelStrategyPrediction);

    }

    protected abstract ConceptCategory predictConceptCategory(final ConceptCategory frameworkDecision,
                                                              final ClusterSummary targetClusterSummary,
                                                              final List<ClusterSummary> knownClusterSummaries,
                                                              final Set<Integer> knownLabels);

    public LowLevelStrategy getLowLevelStrategy() {
        return lowLevelStrategy;
    }

    public Strategy setLowLevelStrategy(LowLevelStrategy lowLevelStrategy) {
        this.lowLevelStrategy = lowLevelStrategy;
        return this;
    }
}
