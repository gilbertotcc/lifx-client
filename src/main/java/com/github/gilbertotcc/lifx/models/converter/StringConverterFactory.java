package com.github.gilbertotcc.lifx.models.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class StringConverterFactory<T> extends Converter.Factory {

    private final Class<T> sourceType;
    private final Converter<T, String> converter;

    private StringConverterFactory(final Class<T> sourceType, final Converter<T, String> converter) {
        this.sourceType = sourceType;
        this.converter = converter;
    }

    public static <T> Converter.Factory of(final Class<T> clazz, final Converter<T, String> converter) {
        return new StringConverterFactory<>(clazz, converter);
    }

    @Override
    public Converter<T, String> stringConverter(final Type type, final Annotation[] annotations, final Retrofit retrofit) {
        final Class<?> rawType = getRawType(type);
        return sourceType.equals(rawType) ? converter : null;
    }
}
