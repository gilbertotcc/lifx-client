package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class ErrorModelTest {

  private static final String JSON_FILE = "/json/response_body/error_response.json";

  @Test
  void deserializeLightShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE);
    ErrorModel errorModel = deserializeJson(json, ErrorModel.class);

    assertEquals("color is missing", errorModel.errorMessage());
    assertEquals(1, errorModel.details().size());
    ErrorModel.ErrorDetail errorDetail = errorModel.details().get(0);
    assertEquals("color", errorDetail.field());
    assertEquals("is missing", errorDetail.messages().get(0));
  }

  @Test
  void deserializeWithoutDetailsShouldReturnEmptyDetails() {
    ErrorModel errorModel = deserializeJson("{\"error\":\"bad\"}", ErrorModel.class);

    assertEquals("bad", errorModel.errorMessage());
    assertEquals(List.of(), errorModel.details());
  }
}
