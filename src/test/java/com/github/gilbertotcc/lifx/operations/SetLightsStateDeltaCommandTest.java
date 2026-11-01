package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.StateDelta;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

class SetLightsStateDeltaCommandTest extends OperationsTestFixtures {

  @Test
  void setLightsStateDeltaShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/state/delta"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .withRequestBody(equalToJson(loadJsonFromFile("/json/request_body/state_delta.json")))
      .willReturn(aResponse().withBody(response)));

    final StateDelta stateDelta = StateDelta.builder()
      .power(Power.ON)
      .duration(Duration.ofMillis(2500))
      .infrared(0.25)
      .hue(120.0)
      .saturation(0.5)
      .brightness(0.1)
      .kelvin(500.0)
      .build();
    var input = new SetLightsStateDeltaInput(all(), stateDelta);

    final List<Result> results = someResultOf(authorizedClient.setLightsStateDelta(input));

    assertEquals(1, results.size());
  }
}
