package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ErrorTest {

  private static final String JSON_FILE = "/json/response_body/error_response.json";

  @Test
  void deserializeLightShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE);
    Error error = deserializeJson(json, Error.class);

    assertEquals("color is missing", error.getErrorMessage());
    assertEquals(1, error.getDetails().size());
    Error.ErrorDetail errorDetail = error.getDetails().get(0);
    assertEquals("color", errorDetail.getField());
    assertEquals("is missing", errorDetail.getMessages().get(0));
  }
}
