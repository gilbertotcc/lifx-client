package com.github.gilbertotcc.lifx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LifxClientTestIntegrationTest {

  private static LifxClient lifxClient;

  private static String lightId = null;
  private static Power lightPower = null;

  @BeforeClass
  public static void initLifxClient() {
    String accessToken = Optional.ofNullable(System.getenv("IT_ACCESS_TOKEN")).filter(StringUtils::isNotBlank).get();
    lifxClient = LifxClient.newLifxClientFor(accessToken);
  }

  @AfterClass
  public static void tearDownITs() {
    // TODO Reset the light state
  }

  @Test
  public void test00_listLightsShouldSuccess() {
    List<Light> lights = lifxClient.listLights(LightsSelector.ALL);

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
  public void test01_setLightsStateShouldSuccess() {

    State state = State.builder()
      .power(lightPower)
      .build();

    List<Result> results = lifxClient.setLightsState(LightsSelector.id(lightId), state);

    assertEquals(1, results.size());
    assertEquals(Result.Status.OK, results.get(0).getStatus());
  }

  @Test
  public void test02_toggleLightsPowerShouldSuccess() {
    List<Result> results = lifxClient.toggleLightsPower(LightsSelector.id(lightId), Duration.ofSeconds(10));

    assertEquals(1, results.size());
    assertEquals(Result.Status.OK, results.get(0).getStatus());
  }

  @Test
  public void test03_validateGrenColorShouldSuccess() {
    Color color = lifxClient.validateColor("green");

    assertEquals(120, color.getHue(), 0.001);
    assertEquals(1, color.getSaturation(), 0.001);
    assertNull(color.getBrightness());
    assertNull(color.getKelvin());

  }

  @Test(expected = LifxCallException.class)
  public void listLightsShouldFail() {
    LifxClient lifxClient = LifxClient.newLifxClientFor("unknownAccessToken");
    lifxClient.listLights(LightsSelector.ALL);
  }
}
