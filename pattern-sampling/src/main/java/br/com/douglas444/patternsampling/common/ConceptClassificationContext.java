package br.com.douglas444.patternsampling.common;

import br.com.douglas444.datastreamutils.interceptor.Context;
import br.com.douglas444.mltk.datastructure.ClusterSummary;
import br.com.douglas444.mltk.datastructure.Sample;
import br.com.douglas444.patternsampling.types.ConceptCategory;

import java.util.List;
import java.util.Set;

public class ConceptClassificationContext implements Context {

    private ConceptCategory decision;
    private ClusterSummary targetClusterSummary;
    private List<ClusterSummary> knownClusterSummaries;
    private Set<Integer> knownLabels;
    private List<Sample> targetSamples;
    private Runnable defaultAction;

    public ConceptCategory getDecision() {
        return decision;
    }

    public ConceptClassificationContext setDecision(ConceptCategory decision) {
        this.decision = decision;
        return this;
    }

    public ClusterSummary getTargetClusterSummary() {
        return targetClusterSummary;
    }

    public ConceptClassificationContext setTargetClusterSummary(ClusterSummary targetClusterSummary) {
        this.targetClusterSummary = targetClusterSummary;
        return this;
    }

    public List<ClusterSummary> getKnownClusterSummaries() {
        return knownClusterSummaries;
    }

    public ConceptClassificationContext setKnownClusterSummaries(List<ClusterSummary> knownClusterSummaries) {
        this.knownClusterSummaries = knownClusterSummaries;
        return this;
    }

    public Set<Integer> getKnownLabels() {
        return knownLabels;
    }

    public ConceptClassificationContext setKnownLabels(Set<Integer> knownLabels) {
        this.knownLabels = knownLabels;
        return this;
    }

    public List<Sample> getTargetSamples() {
        return targetSamples;
    }

    public ConceptClassificationContext setTargetSamples(List<Sample> targetSamples) {
        this.targetSamples = targetSamples;
        return this;
    }

    public Runnable getDefaultAction() {
        return defaultAction;
    }

    public ConceptClassificationContext setDefaultAction(Runnable defaultAction) {
        this.defaultAction = defaultAction;
        return this;
    }
}
