package com.github.gilbertotcc.lifx.util;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.serializeObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.StateDelta;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import tools.jackson.core.JacksonException;

class JacksonUtilsTest {

  @Test
  void serializeStateDeltaShouldOmitNullFields() throws JSONException {
    StateDelta stateDelta = StateDelta.builder().brightness(0.5).build();

    JSONAssert.assertEquals("{\"brightness\":0.5}", serializeObject(stateDelta), true);
  }

  @Test
  void serializeBreatheEffectShouldOmitNullFields() throws JSONException {
    BreatheEffect breatheEffect = BreatheEffect.builder().color("red").build();

    JSONAssert.assertEquals("{\"color\":\"red\"}", serializeObject(breatheEffect), true);
  }

  @Test
  void deserializeNullPrimitiveShouldFail() {
    String json = """
      {
        "id": "1234",
        "color": {
          "hue": 120.0,
          "saturation": 1.0,
          "kelvin": null
        }
      }
      """;

    assertThrows(JacksonException.class, () -> deserializeJson(json, Light.class));
  }

  @Test
  void deserializeMissingPrimitiveShouldUseDefaultValue() {
    String json = """
      {
        "id": "1234",
        "color": {
          "hue": 120.0,
          "saturation": 1.0
        }
      }
      """;

    Light light = deserializeJson(json, Light.class);

    assertEquals(0, light.color().kelvin());
  }
}
