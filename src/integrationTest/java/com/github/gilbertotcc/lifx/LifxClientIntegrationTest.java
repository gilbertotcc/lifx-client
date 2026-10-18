package com.github.gilbertotcc.lifx;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.ListLightsOutput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorInput;
import io.vavr.control.Either;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// The toggle runs last, so only the teardown has to wait for its fade
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LifxClientIntegrationTest {

  private static final Duration TOGGLE_DURATION = Duration.ofSeconds(2);
  private static final int RESTORE_POLL_ATTEMPTS = 10;
  private static final Duration RESTORE_POLL_INTERVAL = Duration.ofSeconds(1);

  private LifxClient lifxClient;

  // Connected light the tests change, and its power before any change; null when the account has none
  private String lightId;
  private Power originalPower;
  // Set before each state-changing request: it may reach the light even if the call reports a failure
  private boolean lightChanged;

  @BeforeAll
  void setUp() {
    String accessToken = Optional.ofNullable(System.getenv("LIFX_PAT"))
      .filter(value -> !value.isBlank())
      .orElseThrow(() -> new IllegalStateException("LIFX_PAT is not set"));
    lifxClient = LifxClient.newLifxClientFor(accessToken);

    // A failed listing is reported by listLightsShouldSucceed; the tests that need a light are skipped
    lifxClient.listLights(ListLightsInput.ALL_LIGHTS)
      .fold(error -> List.<Light>of(), ListLightsOutput::getLights)
      .stream()
      .filter(Light::isConnected)
      .filter(light -> light.getId() != null && light.getPower() != null)
      .findFirst()
      .ifPresent(light -> {
        lightId = light.getId();
        originalPower = light.getPower();
      });
  }

  @AfterAll
  void restoreLight() throws InterruptedException {
    if (lightId == null || !lightChanged) {
      return;
    }
    // The cloud acknowledges a toggle before its fade ends; let it end so it cannot race with the restore
    Thread.sleep(TOGGLE_DURATION.plusSeconds(1).toMillis());

    State state = State.builder().power(originalPower).duration(Duration.ZERO).build();
    var input = new SetLightsStateInput(Selectors.byId(lightId), state);
    assertSingleOkResult(lightId, assertRight(lifxClient.setLightsState(input)).getResult());

    // The listing can lag behind commands
    Power power = null;
    for (int attempt = 0; attempt < RESTORE_POLL_ATTEMPTS && power != originalPower; attempt++) {
      Thread.sleep(RESTORE_POLL_INTERVAL.toMillis());
      power = currentPower();
    }
    assertEquals(originalPower, power, "Light " + lightId + " was not restored");
  }

  @Test
  @Order(1)
  void listLightsShouldSucceed() {
    List<Light> lights = assertRight(lifxClient.listLights(ListLightsInput.ALL_LIGHTS)).getLights();

    assertFalse(lights.isEmpty(), "The account has no lights");
    assertEquals(lights.size(), lights.stream().map(Light::getId).distinct().count(), "Light ids are not unique");
    lights.forEach(LifxClientIntegrationTest::assertWellFormed);
  }

  @Test
  @Order(2)
  void setLightsStateShouldSucceed() {
    assumeLightAvailable();
    State state = State.builder().power(opposite(originalPower)).build();
    var input = new SetLightsStateInput(Selectors.byId(lightId), state);

    lightChanged = true;
    List<Result> results = assertRight(lifxClient.setLightsState(input)).getResult();

    assertSingleOkResult(lightId, results);
  }

  @Test
  @Order(3)
  void toggleLightsPowerShouldSucceed() {
    assumeLightAvailable();
    var input = new ToggleLightsPowerInput(Selectors.byId(lightId), TOGGLE_DURATION);

    lightChanged = true;
    List<Result> results = assertRight(lifxClient.toggleLightsPower(input)).getResult();

    assertSingleOkResult(lightId, results);
  }

  @Test
  void validateGreenColorShouldSucceed() {
    var input = new ValidateColorInput("green");
    Color color = assertRight(lifxClient.validateColor(input)).getColor();

    assertEquals(120, color.getHue(), 0.001);
    assertEquals(1, color.getSaturation(), 0.001);
    assertNull(color.getBrightness());
    assertNull(color.getKelvin());
  }

  @Test
  void listLightsShouldFailForInvalidToken() {
    var result = LifxClient.newLifxClientFor("unknownAccessToken").listLights(ListLightsInput.ALL_LIGHTS);

    assertTrue(result.isLeft(), "Expected the call to fail");
    // CallExecutor wraps HTTP errors in a LifxCallException
    var error = assertInstanceOf(LifxErrorException.class, result.getLeft().getCause());
    assertEquals(LifxErrorType.UNAUTHORIZED, error.getType());
  }

  private void assumeLightAvailable() {
    assumeTrue(lightId != null, "No connected light on the account");
  }

  private Power currentPower() {
    var input = new ListLightsInput(Selectors.byId(lightId));
    return assertRight(lifxClient.listLights(input)).getLights().stream()
      .findFirst()
      .map(Light::getPower)
      .orElse(null);
  }

  private static Power opposite(final Power power) {
    return power == Power.ON ? Power.OFF : Power.ON;
  }

  private static <T> T assertRight(final Either<LifxCallException, T> result) {
    if (result.isLeft()) {
      fail("LIFX call failed", result.getLeft());
    }
    return result.get();
  }

  private static void assertSingleOkResult(final String id, final List<Result> results) {
    assertEquals(1, results.size(), "Number of results");
    assertEquals(id, results.get(0).getId());
    assertEquals(Result.Status.OK, results.get(0).getStatus());
  }

  private static void assertWellFormed(final Light light) {
    assertNotNull(light.getId(), "Light without id");
    assertAll("light " + light.getId(),
      () -> assertFalse(light.getId().isBlank(), "id is blank"),
      () -> assertNotNull(light.getLabel(), "label"),
      () -> assertNotNull(light.getPower(), "power"),
      () -> assertBetween(0, 1, light.getBrightness(), "brightness"),
      () -> assertColor(light.getColor()),
      () -> assertNotNull(light.getGroup(), "group"),
      () -> assertNotNull(light.getLocation(), "location"),
      () -> assertNotNull(light.getProduct(), "product"),
      () -> assertNotNull(light.getLastSeen(), "last_seen"),
      () -> assertTrue(light.getSecondsSinceSeen() >= 0, "seconds_since_seen is negative"));
  }

  private static void assertColor(final Light.Color color) {
    assertNotNull(color, "color");
    assertAll("color",
      () -> assertBetween(0, 360, color.getHue(), "hue"),
      () -> assertBetween(0, 1, color.getSaturation(), "saturation"),
      // Kelvin range documented by the LIFX API
      () -> assertBetween(1500, 9000, color.getKelvin(), "kelvin"));
  }

  private static void assertBetween(final double min, final double max, final double actual, final String name) {
    assertTrue(actual >= min && actual <= max, () -> name + " " + actual + " is not in [" + min + ", " + max + "]");
  }
}
