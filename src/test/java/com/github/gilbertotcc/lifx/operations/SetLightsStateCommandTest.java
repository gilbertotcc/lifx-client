package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.State;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

class SetLightsStateCommandTest extends OperationsTestFixtures {

  @Test
  void setLightsStateShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/set_state_OK.json");

    wireMockServer.stubFor(put(urlEqualTo("/v1/lights/all/state"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final var lightsSelector = all();
    final var state = State.builder().power(Power.ON).build();
    final var input = new SetLightsStateInput(lightsSelector, state);

    final var results = AUTHORIZED_CLIENT.setLightsState(input).get().getResult();

    assertEquals(2, results.size());
  }
}
