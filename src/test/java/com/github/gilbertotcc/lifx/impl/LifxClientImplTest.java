package com.github.gilbertotcc.lifx.impl;

import static com.github.gilbertotcc.lifx.testutil.JacksonTestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

public class LifxClientImplTest {

    private static final int PORT = 9999;
    private static final String BASE_URL = String.format("http://localhost:%d", PORT);

    private static final String VALID_ACCESS_TOKEN = "validAccessToken";

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(options().port(PORT));

    @Test
    public void listAllLightsShouldSuccess() throws IOException {
        final String response = loadJsonFromFile("/json/response_body/list_lights_OK.json");

        stubFor(get(urlEqualTo("/v1/lights/all"))
                .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
                .willReturn(aResponse().withBody(response)));

        final LifxClient lifxClient = LifxClientImpl.createNewClientFor(BASE_URL, VALID_ACCESS_TOKEN);
        final List<Light> lights = lifxClient.listLights(LightsSelector.ALL);

        assertEquals(1, lights.size());
    }

    @Test
    public void toggleAllLightsPowerShouldSuccess() throws IOException {
        final String response = loadJsonFromFile("/json/response_body/results_OK.json");

        stubFor(post(urlEqualTo("/v1/lights/all/toggle"))
                .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
                .willReturn(aResponse().withBody(response)));

        final LifxClient lifxClient = LifxClientImpl.createNewClientFor(BASE_URL, VALID_ACCESS_TOKEN);
        final List<Result> results = lifxClient.toggleLightsPower(LightsSelector.ALL, Duration.ofSeconds(1L));

        assertEquals(1, results.size());
    }

    @Test
    public void createNewlifxClientImplShouldSuccess() {
        LifxClientImpl lifxClient = LifxClientImpl.createNewClientFor("accessToken");
        assertNotNull(lifxClient);
    }
}
