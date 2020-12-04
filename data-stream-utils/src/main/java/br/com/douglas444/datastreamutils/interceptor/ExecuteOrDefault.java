package br.com.douglas444.datastreamutils.interceptor;

@FunctionalInterface
public interface ExecuteOrDefault<T, U> {
    U executeOrDefault(T t);
}
