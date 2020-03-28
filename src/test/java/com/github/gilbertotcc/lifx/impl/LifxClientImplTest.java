package com.github.gilbertotcc.lifx.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

class LifxClientImplTest {

  private static final int PORT = 9999;

  private WireMockServer wireMockServer;

  @BeforeEach
  void setup() {
    wireMockServer = new WireMockServer(PORT);
    wireMockServer.start();
  }

  @AfterEach
  void teardown() {
    wireMockServer.stop();
  }

  @Test
  void createNewlifxClientImplShouldSuccess() {
    LifxClientImpl lifxClient = LifxClientImpl.createNewClientFor("accessToken");
    assertNotNull(lifxClient);
  }
}
