package com.github.gilbertotcc.lifx.util;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.serializeObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.util.List;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.TogglePower;
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

  @Test
  void serializeStateShouldWritePowerLabelAndDurationInSeconds() throws JSONException {
    State state = State.builder().power(Power.ON).duration(Duration.ofSeconds(3)).build();

    JSONAssert.assertEquals("{\"power\":\"on\",\"duration\":3.0}", serializeObject(state), true);
  }

  @Test
  void serializeStateShouldTruncateDurationToWholeSeconds() throws JSONException {
    State state = State.builder().power(Power.OFF).duration(Duration.ofMillis(1500)).build();

    JSONAssert.assertEquals("{\"power\":\"off\",\"duration\":1.0}", serializeObject(state), true);
  }

  @Test
  void serializeStateDeltaShouldWritePowerLabelAndDurationInSeconds() throws JSONException {
    StateDelta stateDelta = StateDelta.builder().power(Power.OFF).duration(Duration.ofSeconds(2)).build();

    JSONAssert.assertEquals("{\"power\":\"off\",\"duration\":2.0}", serializeObject(stateDelta), true);
  }

  @Test
  void serializeEffectPeriodShouldWriteSeconds() throws JSONException {
    BreatheEffect breatheEffect = BreatheEffect.builder().period(Duration.ofSeconds(5)).build();

    JSONAssert.assertEquals("{\"period\":5.0}", serializeObject(breatheEffect), true);
  }

  @Test
  void serializeTogglePowerShouldWriteDurationInSeconds() throws JSONException {
    JSONAssert.assertEquals("{\"duration\":4.0}", serializeObject(TogglePower.in(Duration.ofSeconds(4))), true);
  }

  @Test
  void serializeCycleShouldWriteDirectionLabel() throws JSONException {
    Cycle cycle = Cycle.of(List.of(State.builder().power(Power.ON).build()), null, Cycle.Direction.BACKWARD);

    JSONAssert.assertEquals(
      "{\"states\":[{\"power\":\"on\"}],\"direction\":\"backward\"}", serializeObject(cycle), true);
  }

  @Test
  void serializeLightsStateShouldWriteSelectorIdentifier() throws JSONException {
    LightsState lightsState = LightsState.builder()
      .lightSelector(Selectors.byLabel("Desk"))
      .power(Power.ON)
      .build();

    JSONAssert.assertEquals(
      "{\"selector\":\"label:Desk\",\"power\":\"on\"}", serializeObject(lightsState), true);
  }

  @Test
  void deserializeLightPowerShouldMapLabel() {
    Light light = deserializeJson("{\"id\":\"1\",\"power\":\"on\"}", Light.class);

    assertEquals(Power.ON, light.power());
  }

  @Test
  void deserializeLightBlankPowerShouldBeNull() {
    Light light = deserializeJson("{\"id\":\"1\",\"power\":\" \"}", Light.class);

    assertNull(light.power());
  }

  @Test
  void deserializeResultStatusShouldMapLabel() {
    Result result = deserializeJson("{\"id\":\"1\",\"status\":\"timed_out\"}", Result.class);

    assertEquals(Result.Status.TIMED_OUT, result.status());
  }

  @Test
  void deserializeResultBlankStatusShouldBeNull() {
    Result result = deserializeJson("{\"id\":\"1\",\"status\":\"\"}", Result.class);

    assertNull(result.status());
  }
}
