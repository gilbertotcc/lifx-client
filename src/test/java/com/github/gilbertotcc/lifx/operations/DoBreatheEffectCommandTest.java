package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Result;
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

class DoBreatheEffectCommandTest extends OperationsTestFixtures {

  @Test
  void doBreatheEffectShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/effects/breathe"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .withRequestBody(equalToJson(loadJsonFromFile("/json/request_body/breathe_effect.json")))
      .willReturn(aResponse().withBody(response)));

    final BreatheEffect breatheEffect = BreatheEffect.builder()
      .color("red")
      .fromColor("blue")
      .period(Duration.ofMillis(2500))
      .cycles(3.0)
      .persistEffect(true)
      .powerOn(false)
      .peak(0.75)
      .build();

    var input = new DoBreatheEffectInput(all(), breatheEffect);
    final List<Result> results = someResultOf(authorizedClient.doBreatheEffect(input));

    assertEquals(1, results.size());
  }
}
