package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

class TransitToNextStateCommandTest extends OperationsTestFixtures {

  @Test
  void transitToNextStateShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/cycle"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final Cycle cycle = Cycle.of(
      asList(State.builder().power(Power.ON).build(), State.builder().power(Power.OFF).build()),
      State.builder().build(),
      Cycle.Direction.FORWARD);

    var input = new TransitToNextStateInput(all(), cycle);
    final List<Result> results = AUTHORIZED_CLIENT.transitToNextState(input).get().getResult();

    assertEquals(1, results.size());
  }
}
