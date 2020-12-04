package br.com.douglas444.datastreamutils.interceptor;

import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionOrSupplierInterceptor<T extends Context, U> {

    private T t;
    private Function<T, U> function;

    public void define(final Function<T, U> function) {
        this.function = function;
    }

    public ExecuteOrDefault<Supplier<U>, U> with(T t) {

        this.t = t;

        return (supplier) -> {
            U result;
            if (this.function != null) {
                result = this.function.apply(this.t);
            } else {
                result = supplier.get();
            }
            this.clear();
            return result;
        };

    }

    public void clear() {
        this.t = null;
    }

}
