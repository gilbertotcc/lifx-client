package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.LifxResult;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

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

  static Stream<Named<Function<LifxClient, LifxResult<?>>>> clientCallsWithNullInput() {
    return Stream.of(
      call("listLights", client -> client.listLights(null)),
      call("setLightsState", client -> client.setLightsState(null)),
      call("setLightsStates", client -> client.setLightsStates(null)),
      call("setLightsStateDelta", client -> client.setLightsStateDelta(null)),
      call("toggleLightsPower", client -> client.toggleLightsPower(null)),
      call("doBreatheEffect", client -> client.doBreatheEffect(null)),
      call("doPulseEffect", client -> client.doPulseEffect(null)),
      call("transitToNextState", client -> client.transitToNextState(null)),
      call("validateColor", client -> client.validateColor(null)));
  }

  private static Named<Function<LifxClient, LifxResult<?>>> call(
    final String name, final Function<LifxClient, LifxResult<?>> call) {
    return Named.of(name, call);
  }

  @ParameterizedTest
  @MethodSource("clientCallsWithNullInput")
  void clientMethodShouldThrowNullPointerExceptionForNullInput(final Function<LifxClient, LifxResult<?>> call) {
    final LifxClient client = LifxClientImpl.createNewClientFor("accessToken");

    final var exception = assertThrows(NullPointerException.class, () -> call.apply(client));

    assertEquals("input", exception.getMessage());
  }
}
