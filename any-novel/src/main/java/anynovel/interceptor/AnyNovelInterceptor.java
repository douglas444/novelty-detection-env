package anynovel.interceptor;

import br.com.douglas444.datastreamutils.interceptor.ConsumerOrRunnableInterceptor;
import br.com.douglas444.patternsampling.common.ConceptClassificationContext;

public class AnyNovelInterceptor {

    public final ConsumerOrRunnableInterceptor<ConceptClassificationContext> NOVELTY_DETECTION_AL_FRAMEWORK;

    public AnyNovelInterceptor() {

        this.NOVELTY_DETECTION_AL_FRAMEWORK = new ConsumerOrRunnableInterceptor<>();

    }
}
