package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static java.lang.String.format;

public class OperationsTestFixtures {

  private static final int PORT = 9999;
  private static final String BASE_URL = format("http://localhost:%d", PORT);

  protected static final String VALID_ACCESS_TOKEN = "validAccessToken";
  protected static final LifxClient AUTHORIZED_CLIENT = authorizedClient();

  protected WireMockServer wireMockServer;

  private static LifxClient authorizedClient() {
    return LifxClientImpl.createNewClientFor(BASE_URL, VALID_ACCESS_TOKEN);
  }

  @BeforeEach
  void setup() {
    wireMockServer = new WireMockServer(PORT);
    wireMockServer.start();
  }

  @AfterEach
  void teardown() {
    wireMockServer.stop();
  }
}
