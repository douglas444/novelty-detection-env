package br.com.douglas444.datastreamutils.interceptor;

import java.util.function.Consumer;

public class ConsumerOrRunnableInterceptor<T extends Context> {

    private T t;
    private Consumer<T> consumer;

    public void define(final Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public ExecuteOrDefault<Runnable, ?> with(T t) {

        this.t = t;

        return (runnable) -> {
            if (this.consumer != null) {
                this.consumer.accept(this.t);
            } else {
                runnable.run();
            }
            this.clear();
            return null;
        };

    }

    public void clear() {
        this.t = null;
    }

}
