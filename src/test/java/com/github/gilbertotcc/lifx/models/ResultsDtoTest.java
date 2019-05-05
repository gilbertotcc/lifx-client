package com.github.gilbertotcc.lifx.models;

import static com.github.gilbertotcc.lifx.testutil.TestUtils.deserializeJson;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

class ResultsDtoTest {

  private static final String JSON_FILE_SET_STATE = "/json/response_body/set_state_OK.json";
  private static final String JSON_FILE_SET_STATES = "/json/response_body/set_states_OK.json";

  @Test
  void deserializeSetStateOkShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE_SET_STATE);
    ResultsDto<Result> resultsDto = deserializeJson(json, new TypeReference<ResultsDto<Result>>() {
    });

    assertEquals(2, resultsDto.getResults().size());
    Result resultAtIndex0 = resultsDto.getResults().get(0);
    assertEquals("d3b2f2d97452", resultAtIndex0.getId());
    assertEquals("Left Lamp", resultAtIndex0.getLabel());
    assertEquals(Result.Status.TIMED_OUT, resultAtIndex0.getStatus());
    Result resultAtIndex1 = resultsDto.getResults().get(1);
    assertEquals("da2c63c353b8", resultAtIndex1.getId());
    assertEquals("Right Lamp", resultAtIndex1.getLabel());
    assertEquals(Result.Status.OFFLINE, resultAtIndex1.getStatus());
  }

  @Test
  void deserializeSetStatesOkShouldSuccess() throws IOException {
    String json = loadJsonFromFile(JSON_FILE_SET_STATES);
    ResultsDto<OperationResult> resultsDto = deserializeJson(json, new TypeReference<ResultsDto<OperationResult>>() {
    });

    assertEquals(2, resultsDto.getResults().size());
    OperationResult operationResult0 = resultsDto.getResults().get(0);
    assertEquals("[selector 1]", operationResult0.getOperation().getSelector());
    assertEquals(2, operationResult0.getResults().size());
    OperationResult operationResult1 = resultsDto.getResults().get(1);
    assertEquals("[selector 2]", operationResult1.getOperation().getSelector());
    assertTrue(operationResult1.getResults().isEmpty());
    assertEquals("not found", operationResult1.getError());
  }
}
