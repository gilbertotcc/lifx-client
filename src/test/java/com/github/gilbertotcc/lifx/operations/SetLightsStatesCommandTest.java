package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SetLightsStatesCommandTest extends OperationsTestFixtures {

  @Test
  void setLightsStatesShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/set_states_OK.json");

    wireMockServer.stubFor(put(urlEqualTo("/v1/lights/states"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .withRequestBody(equalToJson(loadJsonFromFile("/json/request_body/lights_states.json")))
      .willReturn(aResponse().withBody(response)));

    final State state0 = State.builder()
      .power(Power.ON)
      .color("red")
      .brightness(0.75)
      .duration(Duration.ofMillis(1500))
      .infrared(0.5)
      .fast(false)
      .build();
    final State state1 = State.builder()
      .power(Power.OFF)
      .color("green")
      .brightness(0.25)
      .duration(Duration.ofSeconds(3))
      .infrared(1.0)
      .fast(false)
      .build();
    final State defaults = State.builder()
      .power(Power.ON)
      .color("white")
      .brightness(0.5)
      .duration(Duration.ofSeconds(2))
      .infrared(0.0)
      .fast(false)
      .build();
    final LightsState lightsState0 = LightsState.of(Selectors.byId("aaaaa"), state0);
    final LightsState lightsState1 = LightsState.of(Selectors.byId("bbbbb"), state1);
    var input = new SetLightsStatesInput(List.of(lightsState0, lightsState1), defaults, true);

    final List<OperationResult> results = someResultOf(authorizedClient.setLightsStates(input));

    assertEquals(2, results.size());
  }
}
