package com.github.gilbertotcc.lifx.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import tools.jackson.databind.ObjectWriter;

/**
 * Retrofit converter for Jackson 3, which Retrofit's own {@code converter-jackson} does not support.
 * Jackson 3 exceptions are unchecked, so they are wrapped in {@link IOException} to keep reporting
 * (de)serialization failures as I/O failures of the call.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class JacksonConverterFactory extends Converter.Factory {

  private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");

  private final ObjectMapper objectMapper;

  static JacksonConverterFactory create(final ObjectMapper objectMapper) {
    return new JacksonConverterFactory(objectMapper);
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(final Type type,
                                                          final Annotation[] annotations,
                                                          final Retrofit retrofit) {
    final ObjectReader reader = objectMapper.readerFor(objectMapper.constructType(type));
    // An empty body (e.g. a 202 answering a fast request) converts to null, which CallExecutor reports as no body
    return body -> {
      try (body) {
        if (body.source().exhausted()) {
          return null;
        }
        return reader.readValue(body.charStream());
      } catch (JacksonException e) {
        throw new IOException("Unable to deserialize response body", e);
      }
    };
  }

  @Override
  public Converter<?, RequestBody> requestBodyConverter(final Type type,
                                                        final Annotation[] parameterAnnotations,
                                                        final Annotation[] methodAnnotations,
                                                        final Retrofit retrofit) {
    final ObjectWriter writer = objectMapper.writerFor(objectMapper.constructType(type));
    return value -> {
      try {
        return RequestBody.create(writer.writeValueAsBytes(value), MEDIA_TYPE);
      } catch (JacksonException e) {
        throw new IOException("Unable to serialize request body", e);
      }
    };
  }
}
