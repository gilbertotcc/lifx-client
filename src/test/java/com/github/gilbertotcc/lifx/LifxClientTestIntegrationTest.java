package com.github.gilbertotcc.lifx;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorInput;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LifxClientTestIntegrationTest {

  private static LifxClient lifxClient;

  private static String lightId = null;
  private static Power lightPower = null;

  @BeforeClass
  public static void initLifxClient() {
    String accessToken = Optional.ofNullable(System.getenv("IT_ACCESS_TOKEN"))
      .filter(StringUtils::isNotBlank)
      .get();
    lifxClient = LifxClient.newLifxClientFor(accessToken);
  }

  @AfterClass
  public static void tearDownITs() {
    // TODO Reset the light state
  }

  @Test
  void test00_listLightsShouldSuccess() {
    var input = new ListLightsInput(all());
    List<Light> lights = lifxClient.listLights(input).get().getLights();

    assertFalse(lights.isEmpty());
    // TODO Add more asserts

    Light connectedLight = lights.stream()
      .filter(Light::isConnected)
      .findFirst()
      .orElseThrow(() -> new NullPointerException("No connected lights"));
    lightId = connectedLight.getId();
    lightPower = Power.ON == connectedLight.getPower() ? Power.OFF : Power.ON; // Very ugly!
  }

  @Test
  void test01_setLightsStateShouldSuccess() {

    State state = State.builder()
      .power(lightPower)
      .build();

    var input = new SetLightsStateInput(Selectors.byId(lightId), state);
    List<Result> results = lifxClient.setLightsState(input).get().getResult();

    assertEquals(1, results.size());
    assertEquals(Result.Status.OK, results.get(0).getStatus());
  }

  @Test
  void test02_toggleLightsPowerShouldSuccess() {
    var input = new ToggleLightsPowerInput(Selectors.byId(lightId), Duration.ofSeconds(10));
    List<Result> results = lifxClient.toggleLightsPower(input).get().getResult();

    assertEquals(1, results.size());
    assertEquals(Result.Status.OK, results.get(0).getStatus());
  }

  @Test
  void test03_validateGrenColorShouldSuccess() {
    var input = new ValidateColorInput("green");
    Color color = lifxClient.validateColor(input).get().getColor();

    assertEquals(120, color.getHue(), 0.001);
    assertEquals(1, color.getSaturation(), 0.001);
    assertNull(color.getBrightness());
    assertNull(color.getKelvin());

  }

  @Test
  void listLightsShouldFail() {
    LifxClient lifxClient = LifxClient.newLifxClientFor("unknownAccessToken");
    var thrownException = Try.ofSupplier(Selectors::all)
      .map(ListLightsInput::new)
      .map(lifxClient::listLights)
      .flatMap(result -> result.toTry(result::getLeft))
      .getCause();
    assertEquals(LifxCallException.class, thrownException.getClass());
  }
}
