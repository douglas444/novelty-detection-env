package br.com.douglas444.minas.interceptor.context;

import br.com.douglas444.datastreamutils.interceptor.Context;
import br.com.douglas444.minas.Classification;
import br.com.douglas444.minas.MicroCluster;
import br.com.douglas444.mltk.datastructure.Sample;

import java.util.List;
import java.util.function.Supplier;

public class DecisionModelContext implements Context {

    private List<MicroCluster> decisionModel;
    private Sample sampleTarget;
    private MicroCluster microClusterTarget;
    private Supplier<Classification> defaultAction;

    public DecisionModelContext DecisionModelContext() {
        return this;
    }

    public List<MicroCluster> getDecisionModel() {
        return decisionModel;
    }

    public DecisionModelContext setDecisionModel(List<MicroCluster> decisionModel) {
        this.decisionModel = decisionModel;
        return this;
    }

    public Sample getSampleTarget() {
        return sampleTarget;
    }

    public DecisionModelContext setSampleTarget(Sample sampleTarget) {
        this.sampleTarget = sampleTarget;
        return this;
    }

    public MicroCluster getMicroClusterTarget() {
        return microClusterTarget;
    }

    public DecisionModelContext setMicroClusterTarget(MicroCluster microClusterTarget) {
        this.microClusterTarget = microClusterTarget;
        return this;
    }

    public Supplier<Classification> getDefaultAction() {
        return defaultAction;
    }

    public DecisionModelContext setDefaultAction(Supplier<Classification> defaultAction) {
        this.defaultAction = defaultAction;
        return this;
    }
}
