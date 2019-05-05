package com.github.gilbertotcc.lifx.impl;

import static com.github.gilbertotcc.lifx.models.Selectors.All;
import static com.github.gilbertotcc.lifx.models.Selectors.IdSelector;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LifxClientImplTest {

  private static final int PORT = 9999;
  private static final String BASE_URL = format("http://localhost:%d", PORT);
  private static final String VALID_ACCESS_TOKEN = "validAccessToken";
  private static final LifxClient AUTHORIZED_CLIENT = authorizedClient();

  private WireMockServer wireMockServer;

  @BeforeEach
  void setup() {
    wireMockServer = new WireMockServer(PORT);
    wireMockServer.start();
  }

  @AfterEach
  void teardown() {
    wireMockServer.stop();
  }

  @Test
  void listAllLightsShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/list_lights_OK.json");

    wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final List<Light> lights = AUTHORIZED_CLIENT.listLights(All());

    assertEquals(1, lights.size());
  }

  @Test
  void toggleAllLightsPowerShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/toggle"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final List<Result> results = AUTHORIZED_CLIENT.toggleLightsPower(All(), Duration.ofSeconds(1L));

    assertEquals(1, results.size());
  }

  @Test
  void setLightsStateShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/set_state_OK.json");

    wireMockServer.stubFor(put(urlEqualTo("/v1/lights/all/state"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final State state = State.builder().power(Power.ON).build();

    final List<Result> results = AUTHORIZED_CLIENT.setLightsState(All(), state);

    assertEquals(2, results.size());
  }

  @Test
  void setLightsStatesShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/set_states_OK.json");

    wireMockServer.stubFor(put(urlEqualTo("/v1/lights/states"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final State state = State.builder().power(Power.ON).build();
    final LightsState lightsState = LightsState.of(IdSelector("aaaaa"), state);
    final LightsStatesDto lightsStatesDto = LightsStatesDto.builder()
      .lightsState(lightsState)
      .build();

    final List<OperationResult> results = AUTHORIZED_CLIENT.setLightsStates(lightsStatesDto);

    assertEquals(2, results.size());
  }

  @Test
  void setLightsStateDeltaShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/state/delta"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final StateDelta stateDelta = StateDelta.builder()
      .power(Power.ON)
      .build();

    final List<Result> results = AUTHORIZED_CLIENT.setLightsStateDelta(All(), stateDelta);

    assertEquals(1, results.size());
  }

  @Test
  void doBreatheEffectShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/effects/breathe"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final BreatheEffect breatheEffect = BreatheEffect.builder()
      .powerOn(true)
      .build();

    final List<Result> results = AUTHORIZED_CLIENT.doBreatheEffect(All(), breatheEffect);

    assertEquals(1, results.size());
  }

  @Test
  void doPulseEffectShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/effects/pulse"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final PulseEffect pulseEffect = PulseEffect.builder()
      .powerOn(true)
      .build();

    final List<Result> results = AUTHORIZED_CLIENT.doPulseEffect(All(), pulseEffect);

    assertEquals(1, results.size());
  }

  @Test
  void transitToNextStateOfShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/cycle"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final Cycle cycle = Cycle.of(
      asList(State.builder().power(Power.ON).build(), State.builder().power(Power.OFF).build()),
      State.builder().build(),
      Cycle.Direction.FORWARD);

    final List<Result> results = AUTHORIZED_CLIENT.transitToNextStateOf(All(), cycle);

    assertEquals(1, results.size());
  }

  @Test
  void validateColorShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/validate_color_OK.json");

    wireMockServer.stubFor(get(urlEqualTo("/v1/color?string=red"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final Color color = AUTHORIZED_CLIENT.validateColor("red");

    assertEquals(0.0, color.getHue(), 0.0001);
    assertEquals(1.0, color.getSaturation(), 0.0001);
  }

  @Test
  void createNewlifxClientImplShouldSuccess() {
    LifxClientImpl lifxClient = LifxClientImpl.createNewClientFor("accessToken");
    assertNotNull(lifxClient);
  }

  private static LifxClient authorizedClient() {
    return LifxClientImpl.createNewClientFor(BASE_URL, VALID_ACCESS_TOKEN);
  }
}
