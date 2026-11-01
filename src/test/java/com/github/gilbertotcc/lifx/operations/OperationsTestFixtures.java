package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.fail;

public class OperationsTestFixtures {

  private static final int PORT = 9999;
  private static final String BASE_URL = format("http://localhost:%d", PORT);

  protected static final String VALID_ACCESS_TOKEN = "validAccessToken";
  protected static final LifxClient AUTHORIZED_CLIENT = authorizedClient();

  protected WireMockServer wireMockServer;

  private static LifxClient authorizedClient() {
    return LifxClientImpl.createNewClientFor(BASE_URL, VALID_ACCESS_TOKEN);
  }

  protected static <T> T someResultOf(final LifxResult<T> result) {
    if (result instanceof LifxResult.Success.Some<T> some) {
      return some.result();
    }
    return fail("Expected a successful result with a value, got " + result);
  }

  protected static <T> LifxCallException failureOf(final LifxResult<T> result) {
    if (result instanceof LifxResult.Failure<T> failure) {
      return failure.lifxCallException();
    }
    return fail("Expected a failure, got " + result);
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
