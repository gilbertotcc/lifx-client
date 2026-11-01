package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.fail;

public class OperationsTestFixtures {

  protected static final String VALID_ACCESS_TOKEN = "validAccessToken";

  protected WireMockServer wireMockServer;
  protected LifxClient authorizedClient;

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
    wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    wireMockServer.start();
    authorizedClient = LifxClientImpl.createNewClientFor(wireMockServer.baseUrl(), VALID_ACCESS_TOKEN);
  }

  @AfterEach
  void teardown() {
    wireMockServer.stop();
  }
}
