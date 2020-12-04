package br.com.douglas444.minas.interceptor;

import br.com.douglas444.datastreamutils.interceptor.ConsumerOrRunnableInterceptor;
import br.com.douglas444.datastreamutils.interceptor.FunctionOrSupplierInterceptor;
import br.com.douglas444.minas.Classification;
import br.com.douglas444.minas.interceptor.context.DecisionModelContext;
import br.com.douglas444.patternsampling.common.ConceptClassificationContext;

public class MINASInterceptor {

    public final ConsumerOrRunnableInterceptor<ConceptClassificationContext> NOVELTY_DETECTION_AL_FRAMEWORK;
    public final FunctionOrSupplierInterceptor<DecisionModelContext, Classification> SAMPLE_CLASSIFIER;
    public final FunctionOrSupplierInterceptor<DecisionModelContext, Classification> MICRO_CLUSTER_CLASSIFIER;

    public MINASInterceptor() {

        this.NOVELTY_DETECTION_AL_FRAMEWORK = new ConsumerOrRunnableInterceptor<>();
        this.SAMPLE_CLASSIFIER = new FunctionOrSupplierInterceptor<>();
        this.MICRO_CLUSTER_CLASSIFIER = new FunctionOrSupplierInterceptor<>();

    }

}
