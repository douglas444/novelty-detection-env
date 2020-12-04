package br.com.douglas444.patternsampling.strategy.common;

import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.patternsampling.common.ConceptClassificationContext;
import br.com.douglas444.patternsampling.common.Oracle;
import br.com.douglas444.patternsampling.types.ConceptCategory;
import br.com.douglas444.patternsampling.types.DecisionCategory;
import br.com.douglas444.patternsampling.types.InterceptionResult;

import java.util.List;
import java.util.Set;

public abstract class Strategy {

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

        return new InterceptionResult(realCategory, context.getDecision(), indicatorConceptCategoryPrediction,
                indicatorDecisionCategoryPrediction);

    }

    protected abstract ConceptCategory predictConceptCategory(final ConceptCategory frameworkDecision,
                                                              final ClusterSummary targetClusterSummary,
                                                              final List<ClusterSummary> knownClusterSummaries,
                                                              final Set<Integer> knownLabels);
}
