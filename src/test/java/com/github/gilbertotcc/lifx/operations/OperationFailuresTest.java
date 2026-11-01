package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.http.Fault;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static com.github.gilbertotcc.lifx.testutil.TestUtils.loadJsonFromFile;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * HTTP and I/O failures of every operation. They all share the {@code CallExecutor} path, hence one parameterized
 * class instead of a copy per operation. 404 is deliberately not used: WireMock also answers 404 to an unmatched
 * request, so a wrong path would pass unnoticed. The {@code *ShouldSuccess} tests pin the HTTP verb, which is why
 * the stubs here match {@code any(...)} method.
 */
class OperationFailuresTest extends OperationsTestFixtures {

  static Stream<Arguments> operations() {
    final var state = State.builder().power(Power.ON).build();
    final var lightsState = LightsState.of(Selectors.byId("aaaaa"), state);
    final var cycle = Cycle.of(
      List.of(state, State.builder().power(Power.OFF).build()),
      State.builder().build(),
      Cycle.Direction.FORWARD);

    return Stream.of(
      arguments("listLights", c -> c.listLights(ListLightsInput.ALL_LIGHTS), "/v1/lights/all"),
      arguments("setLightsState", c -> c.setLightsState(new SetLightsStateInput(all(), state)),
        "/v1/lights/all/state"),
      arguments("setLightsStates",
        c -> c.setLightsStates(new SetLightsStatesInput(List.of(lightsState), null, false)), "/v1/lights/states"),
      arguments("setLightsStateDelta",
        c -> c.setLightsStateDelta(new SetLightsStateDeltaInput(all(), StateDelta.builder().power(Power.ON).build())),
        "/v1/lights/all/state/delta"),
      arguments("toggleLightsPower",
        c -> c.toggleLightsPower(new ToggleLightsPowerInput(all(), Duration.ofSeconds(1L))),
        "/v1/lights/all/toggle"),
      arguments("doBreatheEffect",
        c -> c.doBreatheEffect(new DoBreatheEffectInput(all(), BreatheEffect.builder().powerOn(true).build())),
        "/v1/lights/all/effects/breathe"),
      arguments("doPulseEffect",
        c -> c.doPulseEffect(new DoPulseEffectInput(all(), PulseEffect.builder().powerOn(true).build())),
        "/v1/lights/all/effects/pulse"),
      arguments("transitToNextState", c -> c.transitToNextState(new TransitToNextStateInput(all(), cycle)),
        "/v1/lights/all/cycle"),
      arguments("validateColor", c -> c.validateColor(new ValidateColorInput("red")), "/v1/color?string=red"));
  }

  private static Arguments arguments(
    final String name, final Function<LifxClient, LifxResult<?>> call, final String path) {
    return Arguments.of(Named.of(name, call), path);
  }

  @ParameterizedTest
  @MethodSource("operations")
  void shouldFailWithErrorModelFor4xx(final Function<LifxClient, LifxResult<?>> call, final String path)
    throws IOException {
    final var body = loadJsonFromFile("/json/response_body/error_response.json");
    stubResponse(path, aResponse().withStatus(422).withBody(body));

    final var cause = assertInstanceOf(LifxErrorException.class, failureOf(call.apply(authorizedClient)).getCause());

    assertEquals(LifxErrorType.NOT_PROCESSABLE_ENTITY, cause.getType());
    assertEquals("color is missing", cause.getErrorModel().errorMessage());
  }

  @ParameterizedTest
  @MethodSource("operations")
  void shouldFailWithoutErrorModelFor5xx(final Function<LifxClient, LifxResult<?>> call, final String path) {
    stubResponse(path, aResponse().withStatus(500));

    final var cause = assertInstanceOf(LifxErrorException.class, failureOf(call.apply(authorizedClient)).getCause());

    assertEquals(LifxErrorType.SERVER_ERROR, cause.getType());
    assertNull(cause.getErrorModel());
  }

  @ParameterizedTest
  @MethodSource("operations")
  void shouldFailForIoFailure(final Function<LifxClient, LifxResult<?>> call, final String path) {
    stubResponse(path, aResponse().withFault(Fault.EMPTY_RESPONSE));

    assertInstanceOf(IOException.class, failureOf(call.apply(authorizedClient)).getCause());
  }

  private void stubResponse(final String path, final ResponseDefinitionBuilder response) {
    wireMockServer.stubFor(any(urlEqualTo(path))
      .withHeader("Authorization", equalTo("Bearer " + VALID_ACCESS_TOKEN))
      .willReturn(response));
  }
}
