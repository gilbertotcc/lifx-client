package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;

class LightTest {

  private static final String JSON_FILE = "/json/response_body/list_lights_OK.json";

  @Test
  void deserializeLightShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE);
    List<Light> lights = deserializeJson(json, new TypeReference<List<Light>>() {
    });

    assertEquals(1, lights.size());
    // TODO Add more asserts
  }
}
