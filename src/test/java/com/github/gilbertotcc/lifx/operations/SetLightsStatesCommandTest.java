package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

class SetLightsStatesCommandTest extends OperationsTestFixtures {

  @Test
  void setLightsStatesShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/set_states_OK.json");

    wireMockServer.stubFor(put(urlEqualTo("/v1/lights/states"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final State state = State.builder().power(Power.ON).build();
    final LightsState lightsState0 = LightsState.of(Selectors.byId("aaaaa"), state);
    final LightsState lightsState1 = LightsState.of(Selectors.byId("bbbbb"), state);
    var input = new SetLightsStatesInput(List.of(lightsState0, lightsState1), null, false);

    final List<OperationResult> results = AUTHORIZED_CLIENT.setLightsStates(input).get().getResult();

    assertEquals(2, results.size());
  }
}
