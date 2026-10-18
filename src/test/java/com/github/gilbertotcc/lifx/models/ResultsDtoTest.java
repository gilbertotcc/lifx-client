package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;

class ResultsDtoTest {

  private static final String JSON_FILE_SET_STATE = "/json/response_body/set_state_OK.json";
  private static final String JSON_FILE_SET_STATES = "/json/response_body/set_states_OK.json";

  @Test
  void deserializeSetStateOkShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE_SET_STATE);
    ResultsDto<Result> resultsDto = deserializeJson(json, new TypeReference<ResultsDto<Result>>() {
    });

    assertEquals(2, resultsDto.results().size());
    Result resultAtIndex0 = resultsDto.results().get(0);
    assertEquals("d3b2f2d97452", resultAtIndex0.id());
    assertEquals("Left Lamp", resultAtIndex0.label());
    assertEquals(Result.Status.TIMED_OUT, resultAtIndex0.status());
    Result resultAtIndex1 = resultsDto.results().get(1);
    assertEquals("da2c63c353b8", resultAtIndex1.id());
    assertEquals("Right Lamp", resultAtIndex1.label());
    assertEquals(Result.Status.OFFLINE, resultAtIndex1.status());
  }

  @Test
  void deserializeSetStatesOkShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE_SET_STATES);
    ResultsDto<OperationResult> resultsDto = deserializeJson(json, new TypeReference<ResultsDto<OperationResult>>() {
    });

    assertEquals(2, resultsDto.results().size());
    OperationResult operationResult0 = resultsDto.results().get(0);
    assertEquals("[selector 1]", operationResult0.operation().selector());
    assertEquals(2, operationResult0.results().size());
    OperationResult operationResult1 = resultsDto.results().get(1);
    assertEquals("[selector 2]", operationResult1.operation().selector());
    assertTrue(operationResult1.results().isEmpty());
    assertEquals("not found", operationResult1.error());
  }
}
