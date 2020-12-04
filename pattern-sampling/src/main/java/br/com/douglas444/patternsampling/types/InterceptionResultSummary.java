package br.com.douglas444.patternsampling.types;

import java.util.List;

public class InterceptionResultSummary {

    private String frameworkOutput;
    private Double parameter1;
    private Double parameter2;

    private int trueKnownIndicator;
    private int falseKnownIndicator;
    private int trueNoveltyIndicator;
    private int falseNoveltyIndicator;

    private int trueKnownFramework;
    private int falseKnownFramework;
    private int trueNoveltyFramework;
    private int falseNoveltyFramework;

    private int trueRisky;
    private int falseRisky;
    private int trueReliable;
    private int falseReliable;

    final List<InterceptionResult> results;

    public InterceptionResultSummary(final String frameworkOutput, final Double parameter1, final Double parameter2,
                                     final List<InterceptionResult> results) {

        this.frameworkOutput = frameworkOutput;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.results = results;

        if (results.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.trueRisky = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.TRUE_RISKY)
                .count();

        this.falseRisky = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.FALSE_RISKY)
                .count();

        this.trueReliable = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.TRUE_RELIABLE)
                .count();

        this.falseReliable = (int) results.stream()
                .filter(result -> result.getConfusionMatrixEnum() == ConfusionMatrixEnum.FALSE_RELIABLE)
                .count();

        //Novel-known indicator

        this.trueKnownIndicator = (int) results.stream()
                .filter(result -> result.getRealCategory() == result.getIndicatorConceptCategoryPrediction())
                .filter(result -> result.getIndicatorConceptCategoryPrediction() == ConceptCategory.KNOWN)
                .count();

        this.falseKnownIndicator = (int) results.stream()
                .filter(result -> result.getRealCategory() != result.getIndicatorConceptCategoryPrediction())
                .filter(result -> result.getIndicatorConceptCategoryPrediction() == ConceptCategory.KNOWN)
                .count();

        this.trueNoveltyIndicator = (int) results.stream()
                .filter(result -> result.getRealCategory() == result.getIndicatorConceptCategoryPrediction())
                .filter(result -> result.getIndicatorConceptCategoryPrediction() == ConceptCategory.NOVELTY)
                .count();

        this.falseNoveltyIndicator = (int) results.stream()
                .filter(result -> result.getRealCategory() != result.getIndicatorConceptCategoryPrediction())
                .filter(result -> result.getIndicatorConceptCategoryPrediction() == ConceptCategory.NOVELTY)
                .count();

        //Novel-known framework

        this.trueKnownFramework = (int) results.stream()
                .filter(result -> result.getRealCategory() == result.getFrameworkPrediction())
                .filter(result -> result.getFrameworkPrediction() == ConceptCategory.KNOWN)
                .count();

        this.falseKnownFramework = (int) results.stream()
                .filter(result -> result.getRealCategory() != result.getFrameworkPrediction())
                .filter(result -> result.getFrameworkPrediction() == ConceptCategory.KNOWN)
                .count();

        this.trueNoveltyFramework = (int) results.stream()
                .filter(result -> result.getRealCategory() == result.getFrameworkPrediction())
                .filter(result -> result.getFrameworkPrediction() == ConceptCategory.NOVELTY)
                .count();

        this.falseNoveltyFramework = (int) results.stream()
                .filter(result -> result.getRealCategory() != result.getFrameworkPrediction())
                .filter(result -> result.getFrameworkPrediction() == ConceptCategory.NOVELTY)
                .count();

    }


    public double calculatePrecision() {
        return trueRisky / (double) (trueRisky + falseRisky);
    }

    public double calculateRecall() {
        return trueRisky / (double) (trueRisky + falseReliable);
    }

    public double calculateF1() {
        final double recall = calculateRecall();
        final double precision = calculatePrecision();
        return 2 * recall * precision / (recall + precision);
    }

    public int calculateTotal() {
        return trueRisky + falseRisky + trueReliable + falseReliable;
    }

    public int calculatePositives() {
        return trueRisky + falseReliable;
    }


    public String confusionMatrixDecisionCategoryPrediction() {
        return "Confusion Matrix{" +
                "trueRisky=" + trueRisky +
                ", falseRisky=" + falseRisky +
                ", trueReliable=" + trueReliable +
                ", falseReliable=" + falseReliable +
                "}";
    }

    public String confusionMatrixConceptCategoryPrediction_indicator() {
        return "Confusion Matrix{" +
                "trueKnown=" + trueKnownIndicator +
                ", falseKnown=" + falseKnownIndicator +
                ", trueNovelty=" + trueNoveltyIndicator +
                ", falseNovelty=" + falseNoveltyIndicator +
                "}";
    }

    public String confusionMatrixConceptCategoryPrediction_framework() {
        return "Confusion Matrix{" +
                "trueKnown=" + trueKnownFramework +
                ", falseKnown=" + falseKnownFramework +
                ", trueNovelty=" + trueNoveltyFramework +
                ", falseNovelty=" + falseNoveltyFramework +
                "}";
    }

    public double calculateAccuracyForNoveltyPrediction_indicator() {
        return trueNoveltyIndicator / (double) (trueNoveltyIndicator + falseKnownIndicator);
    }

    public double calculateAccuracyForKnownPrediction_indicator() {
        return trueKnownIndicator / (double) (trueKnownIndicator + falseNoveltyIndicator);
    }

    public double calculateAccuracyForNoveltyPrediction_framework() {
        return trueNoveltyFramework / (double) (trueNoveltyFramework + falseKnownFramework);
    }

    public double calculateAccuracyForKnownPrediction_framework() {
        return trueKnownFramework / (double) (trueKnownFramework + falseNoveltyFramework);
    }

    @Override
    public String toString() {

        return
            "Decision category prediction:" +
            "\nTotal of objects (patterns intercepted): " + calculateTotal() +
            "\nPositives (risky): " + calculatePositives() +
            "\nPrecision (efficiency): " + calculatePrecision() +
            "\nRecall (error recover): " + calculateRecall() +
            "\nF1: " + calculateF1() +
            "\n" + confusionMatrixDecisionCategoryPrediction() +
            "\n\nConcept category prediction by the indicator:" +
            "\nAccuracy for known patterns: " + calculateAccuracyForKnownPrediction_indicator() +
            "\nAccuracy for novel patterns: " + calculateAccuracyForNoveltyPrediction_indicator() +
            "\n" + confusionMatrixConceptCategoryPrediction_indicator() +
            "\n\nConcept category prediction by the framework:" +
            "\nAccuracy for known patterns: " + calculateAccuracyForKnownPrediction_framework() +
            "\nAccuracy for novel patterns: " + calculateAccuracyForNoveltyPrediction_framework() +
            "\n" + confusionMatrixConceptCategoryPrediction_framework();



    }

    public String getFrameworkOutput() {
        return frameworkOutput;
    }

    public InterceptionResultSummary setFrameworkOutput(String frameworkOutput) {
        this.frameworkOutput = frameworkOutput;
        return this;
    }

    public Double getParameter1() {
        return parameter1;
    }

    public InterceptionResultSummary setParameter1(Double parameter1) {
        this.parameter1 = parameter1;
        return this;
    }

    public Double getParameter2() {
        return parameter2;
    }

    public InterceptionResultSummary setParameter2(Double parameter2) {
        this.parameter2 = parameter2;
        return this;
    }

    public int getTrueKnownIndicator() {
        return trueKnownIndicator;
    }

    public InterceptionResultSummary setTrueKnownIndicator(int trueKnownIndicator) {
        this.trueKnownIndicator = trueKnownIndicator;
        return this;
    }

    public int getFalseKnownIndicator() {
        return falseKnownIndicator;
    }

    public InterceptionResultSummary setFalseKnownIndicator(int falseKnownIndicator) {
        this.falseKnownIndicator = falseKnownIndicator;
        return this;
    }

    public int getTrueNoveltyIndicator() {
        return trueNoveltyIndicator;
    }

    public InterceptionResultSummary setTrueNoveltyIndicator(int trueNoveltyIndicator) {
        this.trueNoveltyIndicator = trueNoveltyIndicator;
        return this;
    }

    public int getFalseNoveltyIndicator() {
        return falseNoveltyIndicator;
    }

    public InterceptionResultSummary setFalseNoveltyIndicator(int falseNoveltyIndicator) {
        this.falseNoveltyIndicator = falseNoveltyIndicator;
        return this;
    }

    public int getTrueKnownFramework() {
        return trueKnownFramework;
    }

    public InterceptionResultSummary setTrueKnownFramework(int trueKnownFramework) {
        this.trueKnownFramework = trueKnownFramework;
        return this;
    }

    public int getFalseKnownFramework() {
        return falseKnownFramework;
    }

    public InterceptionResultSummary setFalseKnownFramework(int falseKnownFramework) {
        this.falseKnownFramework = falseKnownFramework;
        return this;
    }

    public int getTrueNoveltyFramework() {
        return trueNoveltyFramework;
    }

    public InterceptionResultSummary setTrueNoveltyFramework(int trueNoveltyFramework) {
        this.trueNoveltyFramework = trueNoveltyFramework;
        return this;
    }

    public int getFalseNoveltyFramework() {
        return falseNoveltyFramework;
    }

    public InterceptionResultSummary setFalseNoveltyFramework(int falseNoveltyFramework) {
        this.falseNoveltyFramework = falseNoveltyFramework;
        return this;
    }
}
