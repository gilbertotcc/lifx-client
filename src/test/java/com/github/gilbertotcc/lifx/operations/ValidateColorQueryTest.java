package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Color;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

public class ValidateColorQueryTest extends OperationsTestFixtures {

  @Test
  void validateColorShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/json/response_body/validate_color_OK.json");

    wireMockServer.stubFor(get(urlEqualTo("/v1/color?string=red"))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(aResponse().withBody(response)));

    var input = new ValidateColorInput("red");
    final Color color = AUTHORIZED_CLIENT.validateColor(input).get().getColor();

    assertEquals(0.0, color.getHue(), 0.0001);
    assertEquals(1.0, color.getSaturation(), 0.0001);
  }
}
