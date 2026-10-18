package com.github.gilbertotcc.lifx.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import com.github.gilbertotcc.lifx.util.JacksonUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.junit.jupiter.api.Test;
import retrofit2.Converter;
import retrofit2.Retrofit;
import tools.jackson.core.JacksonException;

class JacksonConverterFactoryTest {

  private static final Annotation[] NO_ANNOTATIONS = new Annotation[0];
  private static final MediaType JSON = MediaType.get("application/json");

  private final JacksonConverterFactory factory = JacksonConverterFactory.create(JacksonUtils.OBJECT_MAPPER);
  private final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost").build();

  @Test
  @SuppressWarnings("unchecked")
  void requestBodyConverterShouldSerializeAsJson() throws IOException {
    final var converter = (Converter<Object, RequestBody>) factory.requestBodyConverter(
      Map.class, NO_ANNOTATIONS, NO_ANNOTATIONS, retrofit);

    final RequestBody body = converter.convert(Map.of("power", "on"));
    final Buffer buffer = new Buffer();
    body.writeTo(buffer);

    assertEquals("application/json; charset=UTF-8", body.contentType().toString());
    assertEquals("{\"power\":\"on\"}", buffer.readUtf8());
  }

  @Test
  void responseBodyConverterShouldDeserializeJson() throws IOException {
    final var converter = factory.responseBodyConverter(Map.class, NO_ANNOTATIONS, retrofit);

    final var payload = """
      {
        "power": "on"
      }
      """;
    final Object value = converter.convert(ResponseBody.create(payload, JSON));

    assertEquals(Map.of("power", "on"), value);
  }

  @Test
  void responseBodyConverterShouldWrapJacksonExceptionInIoException() {
    final var converter = factory.responseBodyConverter(Map.class, NO_ANNOTATIONS, retrofit);

    final IOException exception = assertThrows(IOException.class,
      () -> converter.convert(ResponseBody.create("not json", JSON)));

    assertInstanceOf(JacksonException.class, exception.getCause());
  }
}
