package com.github.gilbertotcc.lifx.models.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import lombok.AllArgsConstructor;
import retrofit2.Converter;
import retrofit2.Retrofit;

@AllArgsConstructor(staticName = "of")
public class StringConverterFactory<T> extends Converter.Factory {

  private final Class<T> sourceType;
  private final Converter<T, String> converter;

  @Override
  public Converter<T, String> stringConverter(final Type type,
                                              final Annotation[] annotations,
                                              final Retrofit retrofit) {
    final Class<?> rawType = getRawType(type);
    return sourceType.equals(rawType) ? converter : null;
  }
}
