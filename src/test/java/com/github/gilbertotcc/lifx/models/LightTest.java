package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    assertEquals(0.002869418, lights.get(0).secondsSinceSeen());
  }

  @Test
  void deserializeNullBrightnessShouldGiveNull() {
    Light light = deserializeJson("{\"id\":\"1\",\"brightness\":null}", Light.class);

    assertNull(light.brightness());
  }

  @Test
  void deserializeFractionalSecondsSinceSeenShouldKeepFraction() throws IOException {
    String json = loadJsonFromFile(JSON_FILE);
    List<Light> lights = deserializeJson(json, new TypeReference<List<Light>>() {
    });

    assertEquals(0.002869418, lights.get(0).secondsSinceSeen());
  }

  @Test
  void deserializeUpperCasePowerShouldGivePowerOn() {
    Light light = deserializeJson("{\"id\":\"1\",\"power\":\"ON\"}", Light.class);

    assertEquals(Power.ON, light.power());
  }
}
