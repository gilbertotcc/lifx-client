package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Light;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ListLightsQueryTest extends OperationsTestFixtures {

  @Test
  void listLightsShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/list_lights_OK.json");

    wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    ListLightsInput listLightsInput = new ListLightsInput(all());
    final List<Light> lights = someResultOf(AUTHORIZED_CLIENT.listLights(listLightsInput));

    assertEquals(1, lights.size());
  }

  @Test
  void listLightsShouldFailForNullLight() {
    wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody("[null]")));

    final var result = AUTHORIZED_CLIENT.listLights(new ListLightsInput(all()));

    assertInstanceOf(IOException.class, failureOf(result).getCause());
  }

  @Test
  void listLightsShouldFailForMalformedResponseBody() {
    wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody("not json")));

    final var result = AUTHORIZED_CLIENT.listLights(new ListLightsInput(all()));

    assertInstanceOf(IOException.class, failureOf(result).getCause());
  }

  @ParameterizedTest
  @ValueSource(ints = {200, 204})
  void listLightsShouldFailForEmptyBody(final int status) {
    wireMockServer.stubFor(get(urlEqualTo("/v1/lights/all"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withStatus(status)));

    final var result = AUTHORIZED_CLIENT.listLights(new ListLightsInput(all()));

    assertInstanceOf(IOException.class, failureOf(result).getCause());
  }
}
