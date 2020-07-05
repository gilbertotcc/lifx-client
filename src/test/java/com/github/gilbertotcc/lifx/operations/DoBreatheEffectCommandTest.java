package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Result;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

class DoBreatheEffectCommandTest extends OperationsTestFixtures {

  @Test
  void doBreatheEffectShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/results_OK.json");

    wireMockServer.stubFor(post(urlEqualTo("/v1/lights/all/effects/breathe"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    final BreatheEffect breatheEffect = BreatheEffect.builder()
      .powerOn(true)
      .build();

    var input = new DoBreatheEffectInput(all(), breatheEffect);
    final List<Result> results = AUTHORIZED_CLIENT.doBreatheEffect(input).get().getResult();

    assertEquals(1, results.size());
  }
}
