package com.github.gilbertotcc.lifx.exception;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LifxErrorTypeTest {

  @ParameterizedTest
  @CsvSource({
    "400, BAD_REQUEST",
    "401, UNAUTHORIZED",
    "403, PERMISSION_DENIED",
    "404, NOT_FOUND",
    "422, NOT_PROCESSABLE_ENTITY",
    "426, UPGRADE_REQUIRED",
    "429, TOO_MANY_REQUESTS",
    "500, SERVER_ERROR",
    "502, SERVER_ERROR",
    "503, SERVER_ERROR",
    "523, SERVER_ERROR",
    "418, UNKNOWN"
  })
  void byHttpCodeShouldMapStatusToType(final int httpCode, final LifxErrorType expected) {
    assertEquals(expected, LifxErrorType.byHttpCode(httpCode));
  }
}
