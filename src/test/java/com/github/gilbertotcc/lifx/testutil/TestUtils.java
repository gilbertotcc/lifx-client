package com.github.gilbertotcc.lifx.testutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gilbertotcc.lifx.util.JacksonUtils;

public final class TestUtils {

  private static final ObjectMapper OBJECT_MAPPER_INSTANCE = JacksonUtils.OBJECT_MAPPER;

  public static String loadJsonFromFile(final String file) throws IOException {
    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(TestUtils.class.getResourceAsStream(file), StandardCharsets.UTF_8))
    ) {
      return reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      throw new IOException(String.format("Error occured while loading/reading file %s", file), e);
    }
  }

  public static <T> T deserializeJson(final String json, Class<T> clazz) throws IOException {
    return OBJECT_MAPPER_INSTANCE.readValue(json, clazz);
  }

  public static <T> T deserializeJson(final String json, TypeReference typeReference) throws IOException {
    return OBJECT_MAPPER_INSTANCE.readValue(json, typeReference);
  }

  public static <T> String serializeObject(final T object) throws JsonProcessingException {
    return OBJECT_MAPPER_INSTANCE.writeValueAsString(object);
  }

  private TestUtils() {
  }
}
