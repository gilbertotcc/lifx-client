package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransitToNextStateCommandTest extends OperationsTestFixtures {

  @Test
  void transitToNextStateShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/cycle"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .withRequestBody(equalToJson(loadJsonFromFile("/json/request_body/cycle.json")))
      .willReturn(aResponse().withBody(response)));

    final Cycle cycle = Cycle.of(
      asList(
        State.builder()
          .power(Power.ON).color("red").brightness(0.75).duration(Duration.ofMillis(1500)).infrared(0.5).fast(false)
          .build(),
        State.builder()
          .power(Power.OFF).color("green").brightness(0.25).duration(Duration.ofSeconds(3)).infrared(1.0).fast(false)
          .build()),
      State.builder()
        .power(Power.ON).color("white").brightness(0.5).duration(Duration.ofSeconds(2)).infrared(0.0).fast(false)
        .build(),
      Cycle.Direction.BACKWARD);

    var input = new TransitToNextStateInput(all(), cycle);
    final List<Result> results = someResultOf(authorizedClient.transitToNextState(input));

    assertEquals(1, results.size());
  }
}
