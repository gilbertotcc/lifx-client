package com.github.gilbertotcc.lifx.util.json;

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
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.TogglePower;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

  // Compares the raw JSON, because JSONAssert may treat 3.0 and 3 as equal
  @ParameterizedTest
  @CsvSource({
    "1150, 1.1",
    "1200, 1.2",
    "560, 0.5",
    "0, 0.0",
    "500, 0.5",
    "1500, 1.5",
    "1999, 1.9",
    "99, 0.0",
    "100, 0.1",
    "3000, 3.0"
  })
  void serializeDurationShouldKeepOneDecimalPlaceAndDropFurtherDigits(final long millis, final String expectedSeconds) {
    String json = serializeObject(TogglePower.in(Duration.ofMillis(millis)));

    assertEquals("{\"duration\":%s}".formatted(expectedSeconds), json);
  }

  @Test
  void serializeDurationShouldIgnoreDigitsBelowOneMillisecond() {
    String json = serializeObject(TogglePower.in(Duration.ofNanos(99_999_999)));

    assertEquals("{\"duration\":0.0}", json);
  }

  @Test
  void serializeLargeDurationShouldNotUseScientificNotation() {
    String json = serializeObject(TogglePower.in(Duration.ofDays(36_525)));

    assertEquals("{\"duration\":3155760000.0}", json);
  }

  @Test
  void serializeDurationOverflowingMillisecondsShouldFail() {
    TogglePower togglePower = TogglePower.in(Duration.ofSeconds(Long.MAX_VALUE));

    assertThrows(JacksonException.class, () -> serializeObject(togglePower));
  }

  @Test
  void serializeStateShouldWriteFractionalDuration() throws JSONException {
    State state = State.builder().power(Power.OFF).duration(Duration.ofMillis(1560)).build();

    JSONAssert.assertEquals("{\"power\":\"off\",\"duration\":1.5}", serializeObject(state), true);
  }

  @Test
  void serializeLightsStateShouldWriteFractionalDuration() throws JSONException {
    LightsState lightsState = LightsState.builder()
      .lightSelector(Selectors.byLabel("Desk"))
      .duration(Duration.ofMillis(1560))
      .build();

    JSONAssert.assertEquals("{\"selector\":\"label:Desk\",\"duration\":1.5}", serializeObject(lightsState), true);
  }

  @Test
  void serializeStateDeltaShouldWriteFractionalDuration() throws JSONException {
    StateDelta stateDelta = StateDelta.builder().duration(Duration.ofMillis(1560)).build();

    JSONAssert.assertEquals("{\"duration\":1.5}", serializeObject(stateDelta), true);
  }

  @Test
  void serializeBreatheEffectShouldWriteFractionalPeriod() throws JSONException {
    BreatheEffect breatheEffect = BreatheEffect.builder().period(Duration.ofMillis(1560)).build();

    JSONAssert.assertEquals("{\"period\":1.5}", serializeObject(breatheEffect), true);
  }

  @Test
  void serializePulseEffectShouldWriteFractionalPeriod() throws JSONException {
    PulseEffect pulseEffect = PulseEffect.builder().period(Duration.ofMillis(1560)).build();

    JSONAssert.assertEquals("{\"period\":1.5}", serializeObject(pulseEffect), true);
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
