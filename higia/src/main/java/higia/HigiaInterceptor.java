package higia;

import br.com.douglas444.datastreamutils.interceptor.ConsumerOrRunnableInterceptor;
import br.com.douglas444.patternsampling.common.ConceptClassificationContext;

public class HigiaInterceptor {

    public final ConsumerOrRunnableInterceptor<ConceptClassificationContext> NOVELTY_DETECTION_AL_FRAMEWORK;

    public HigiaInterceptor() {

        this.NOVELTY_DETECTION_AL_FRAMEWORK = new ConsumerOrRunnableInterceptor<>();

    }

}
