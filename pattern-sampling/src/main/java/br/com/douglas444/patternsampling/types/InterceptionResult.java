package br.com.douglas444.patternsampling.types;

public class InterceptionResult {

    private ConceptCategory realCategory;
    private ConceptCategory frameworkPrediction;
    private ConceptCategory indicatorConceptCategoryPrediction;
    private DecisionCategory indicatorDecisionCategoryPrediction;

    public InterceptionResult(ConceptCategory realCategory,
                              ConceptCategory frameworkPrediction,
                              ConceptCategory indicatorConceptCategoryPrediction,
                              DecisionCategory indicatorDecisionCategoryPrediction) {

        this.realCategory = realCategory;
        this.frameworkPrediction = frameworkPrediction;
        this.indicatorConceptCategoryPrediction = indicatorConceptCategoryPrediction;
        this.indicatorDecisionCategoryPrediction = indicatorDecisionCategoryPrediction;
    }

    public ConfusionMatrixEnum getConfusionMatrixEnum() {

        if (this.indicatorDecisionCategoryPrediction == DecisionCategory.RISKY) {

            if (this.realCategory == this.frameworkPrediction) {
                return ConfusionMatrixEnum.FALSE_RISKY;
            } else {
                return ConfusionMatrixEnum.TRUE_RISKY;
            }

        } else {

            if (this.realCategory == this.frameworkPrediction) {
                return ConfusionMatrixEnum.TRUE_RELIABLE;
            } else {
                return ConfusionMatrixEnum.FALSE_RELIABLE;
            }

        }

    }

    public ConceptCategory getRealCategory() {
        return realCategory;
    }

    public void setRealCategory(ConceptCategory realCategory) {
        this.realCategory = realCategory;
    }

    public ConceptCategory getFrameworkPrediction() {
        return frameworkPrediction;
    }

    public void setFrameworkPrediction(ConceptCategory frameworkPrediction) {
        this.frameworkPrediction = frameworkPrediction;
    }

    public DecisionCategory getIndicatorDecisionCategoryPrediction() {
        return indicatorDecisionCategoryPrediction;
    }

    public void setIndicatorDecisionCategoryPrediction(DecisionCategory indicatorDecisionCategoryPrediction) {
        this.indicatorDecisionCategoryPrediction = indicatorDecisionCategoryPrediction;
    }

    public ConceptCategory getIndicatorConceptCategoryPrediction() {
        return indicatorConceptCategoryPrediction;
    }

    public InterceptionResult setIndicatorConceptCategoryPrediction(ConceptCategory indicatorConceptCategoryPrediction) {
        this.indicatorConceptCategoryPrediction = indicatorConceptCategoryPrediction;
        return this;
    }
}
