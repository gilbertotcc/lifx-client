package com.github.gilbertotcc.lifx;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LifxClientTest {

  @Test
  void newLifxClientForShouldSuccess() {
    LifxClient lifxClient = LifxClient.newLifxClientFor("accessToken");
    assertNotNull(lifxClient);
  }
}
