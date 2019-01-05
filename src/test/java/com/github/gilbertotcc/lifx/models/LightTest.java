package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

public class LightTest {

    private static final String JSON_FILE = "/json/response_body/list_lights_OK.json";

    @Test
    public void deserializeLightShouldSuccess() throws IOException {
        String json = loadJsonFromFile(JSON_FILE);
        List<Light> lights = deserializeJson(json, new TypeReference<List<Light>>() {});

        assertEquals(1, lights.size());
        // TODO Add more asserts
    }
}
