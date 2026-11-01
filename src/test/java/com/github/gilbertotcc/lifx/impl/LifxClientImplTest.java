package com.github.gilbertotcc.lifx.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LifxClientImplTest {

  @Test
  void createNewlifxClientImplShouldSuccess() {
    LifxClientImpl lifxClient = LifxClientImpl.createNewClientFor("accessToken");
    assertNotNull(lifxClient);
  }

  @Test
  void createNewClientForShouldThrowIllegalArgumentExceptionForNullToken() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
      () -> LifxClientImpl.createNewClientFor(null));
    assertEquals("accessToken is null", exception.getMessage());
  }

  @Test
  void createNewClientForShouldThrowIllegalArgumentExceptionForNullBaseUrlAndToken() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
      () -> LifxClientImpl.createNewClientFor(null, null));
    assertEquals("baseUrl is null, accessToken is null", exception.getMessage());
  }
}
