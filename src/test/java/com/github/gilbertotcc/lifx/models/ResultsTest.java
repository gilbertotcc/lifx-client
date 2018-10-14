package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.JacksonTestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.JacksonTestUtils.loadJsonFromFile;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

public class ResultsTest {

    private static final String JSON_FILE = "/json/response_body/results.json";

    @Test
    public void deserializeResultsShouldSuccess() throws IOException {
        String json = loadJsonFromFile(JSON_FILE);
        Results results = deserializeJson(json, Results.class);

        assertEquals(2, results.getResults().size());
        Results.Result resultAtIndex0 = results.getResults().get(0);
        assertEquals("d3b2f2d97452", resultAtIndex0.getId());
        assertEquals("Left Lamp", resultAtIndex0.getLabel());
        assertEquals(Results.Result.Status.TIMED_OUT, resultAtIndex0.getStatus());

        Results.Result resultAtIndex1 = results.getResults().get(1);
        assertEquals("da2c63c353b8", resultAtIndex1.getId());
        assertEquals("Right Lamp", resultAtIndex1.getLabel());
        assertEquals(Results.Result.Status.OFFLINE, resultAtIndex1.getStatus());
    }
}
