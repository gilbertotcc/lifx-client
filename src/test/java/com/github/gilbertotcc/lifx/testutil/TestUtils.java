package com.github.gilbertotcc.lifx.testutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.github.gilbertotcc.lifx.util.JacksonUtils;
import lombok.experimental.UtilityClass;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@UtilityClass
public final class TestUtils {

  private static final ObjectMapper OBJECT_MAPPER_INSTANCE = JacksonUtils.OBJECT_MAPPER;

  public static String loadJsonFromFile(final String file) throws IOException {
    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(TestUtils.class.getResourceAsStream(file), StandardCharsets.UTF_8))
    ) {
      return reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      throw new IOException(String.format("Error occurred while loading/reading file %s", file), e);
    }
  }

  public static <T> T deserializeJson(final String json, Class<T> clazz) {
    return OBJECT_MAPPER_INSTANCE.readValue(json, clazz);
  }

  public static <T> T deserializeJson(final String json, TypeReference<T> typeReference) {
    return OBJECT_MAPPER_INSTANCE.readValue(json, typeReference);
  }

  public static <T> String serializeObject(final T object) {
    return OBJECT_MAPPER_INSTANCE.writeValueAsString(object);
  }
}
