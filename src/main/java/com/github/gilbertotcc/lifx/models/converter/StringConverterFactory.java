package com.github.gilbertotcc.lifx.models.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import retrofit2.Converter;
import retrofit2.Retrofit;

@AllArgsConstructor(staticName = "of")
public class StringConverterFactory<T> extends Converter.Factory {

  private final Class<T> sourceType;
  private final Converter<T, String> converter;

  /**
   * Selects the configured converter only when the requested raw type exactly matches the source class.
   *
   * @return the configured converter, or null to let Retrofit try other factories
   * @throws IllegalArgumentException if Retrofit cannot resolve {@code type} to a raw class
   */
  @Override
  public @Nullable Converter<T, String> stringConverter(final Type type,
                                                        final Annotation[] annotations,
                                                        final Retrofit retrofit) {
    final Class<?> rawType = getRawType(type);
    return sourceType.equals(rawType) ? converter : null;
  }
}
