package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Light;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

class ListLightsQueryTest extends OperationsTestFixtures {

  @Test
  void listLightsShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/list_lights_OK.json");

    wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    ListLightsInput listLightsInput = new ListLightsInput(all());
    final List<Light> lights = AUTHORIZED_CLIENT.listLights(listLightsInput).get().getLights();

    assertEquals(1, lights.size());
  }
}
