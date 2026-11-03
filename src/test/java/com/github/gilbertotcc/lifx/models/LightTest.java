package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
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

  // Light uses native types by owner decision, so a null in a primitive field fails the whole deserialization
  @Test
  void deserializeNullBrightnessShouldFail() {
    String json = """
      {
        "id": "1",
        "brightness": null
      }""";

    assertThrows(JacksonException.class, () -> deserializeJson(json, Light.class));
  }

  // seconds_since_seen is an int by owner decision, so a fractional value loses its fraction
  @Test
  void deserializeFractionalSecondsSinceSeenShouldTruncate() throws IOException {
    String json = loadJsonFromFile(JSON_FILE);
    List<Light> lights = deserializeJson(json, new TypeReference<List<Light>>() {
    });

    assertEquals(0, lights.get(0).secondsSinceSeen());
  }

  @Test
  void deserializeUpperCasePowerShouldGivePowerOn() {
    Light light = deserializeJson(
      """
        {
          "id": "1",
          "power": "ON"
        }""",
      Light.class
    );

    assertEquals(Power.ON, light.power());
  }
}
