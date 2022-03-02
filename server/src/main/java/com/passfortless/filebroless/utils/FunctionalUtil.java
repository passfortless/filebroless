package com.passfortless.filebroless.utils;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

// original author 
// https://stackoverflow.com/questions/53078410/cast-the-content-of-optional-or-streams
public interface FunctionalUtil {

    /**
     * Creates a function that tries to cast to the given class.
     * If the given argument is not an instance of the given class
     * <code>Optional.empty()</code> is returned.
     * This function can be used in an
     * <code>optionalObject.flatMap(Functional.cast(SomeClass.class))</code>
     * context.
     * 
     * @param clazz
     *              given Clazz
     * @return Returns an optional of the given class.
     * @param <X>
     *            generic input argument for the function
     * @param <T>
     *            type of the output of the function
     */
    static <X, T> Function<X, Optional<T>> cast(Class<? extends T> clazz) {
        return x -> {
            if (clazz.isInstance(x)) {
                return Optional.of(clazz.cast(x));
            }
            return Optional.empty();
        };
    }

    /**
     * Creates a function that tries to cast to the given class.
     * If the given argument is not an instance of the given class an empty stream
     * is returned.
     * This function can be used in an
     * <code>stream.flatMap(Functional.castStream(SomeClass.class))</code> context.
     * 
     * @param clazz
     *              given Clazz
     * @return Returns an optional of the given class.
     * @param <X>
     *            generic input argument for the function
     * @param <T>
     *            type of the output of the function
     */
    public static <X, T> Function<X, Stream<T>> castStream(Class<? extends T> clazz) {
        return x -> Stream.of(x) // stream of x.
                .filter(clazz::isInstance) // check if instance
                .map(clazz::cast);
    }
}
