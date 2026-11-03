package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Selectors;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.github.gilbertotcc.lifx.models.Selectors.all;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A null component must fail when the input is built, not in the operation's log line.
 */
class OperationInputsTest {

  private static final State STATE = State.builder().build();
  private static final Cycle CYCLE = Cycle.of(List.of(STATE, STATE), STATE, Cycle.Direction.FORWARD);

  static Stream<Arguments> nullComponents() {
    return Stream.of(
      arguments("ListLightsInput.selector", () -> new ListLightsInput(null), "selector"),
      arguments("SetLightsStateInput.lightSelector", () -> new SetLightsStateInput(null, STATE), "lightSelector"),
      arguments("SetLightsStateInput.state", () -> new SetLightsStateInput(all(), null), "state"),
      arguments("SetLightsStateDeltaInput.lightSelector",
        () -> new SetLightsStateDeltaInput(null, StateDelta.builder().build()), "lightSelector"),
      arguments("SetLightsStateDeltaInput.stateDelta", () -> new SetLightsStateDeltaInput(all(), null), "stateDelta"),
      arguments("ToggleLightsPowerInput.lightSelector",
        () -> new ToggleLightsPowerInput(null, Duration.ZERO), "lightSelector"),
      arguments("ToggleLightsPowerInput.duration", () -> new ToggleLightsPowerInput(all(), null), "duration"),
      arguments("DoBreatheEffectInput.lightSelector",
        () -> new DoBreatheEffectInput(null, BreatheEffect.builder().build()), "lightSelector"),
      arguments("DoBreatheEffectInput.breatheEffect", () -> new DoBreatheEffectInput(all(), null), "breatheEffect"),
      arguments("DoPulseEffectInput.lightSelector",
        () -> new DoPulseEffectInput(null, PulseEffect.builder().build()), "lightSelector"),
      arguments("DoPulseEffectInput.pulseEffect", () -> new DoPulseEffectInput(all(), null), "pulseEffect"),
      arguments("TransitToNextStateInput.lightSelector",
        () -> new TransitToNextStateInput(null, CYCLE), "lightSelector"),
      arguments("TransitToNextStateInput.cycle", () -> new TransitToNextStateInput(all(), null), "cycle"),
      arguments("ValidateColorInput.colorString", () -> new ValidateColorInput(null), "colorString"),
      arguments("LightsState.lightSelector", () -> LightsState.builder().build(), "lightSelector"));
  }

  private static Arguments arguments(final String name, final Executable construction, final String field) {
    return Arguments.of(Named.of(name, construction), field);
  }

  @ParameterizedTest
  @MethodSource("nullComponents")
  void shouldRejectNullComponent(final Executable construction, final String field) {
    final var exception = assertThrows(NullPointerException.class, construction);

    assertEquals(field, exception.getMessage());
  }

  @Test
  void setLightsStatesInputShouldRejectNullList() {
    assertThrows(NullPointerException.class, () -> new SetLightsStatesInput(null, STATE, false));
  }

  @Test
  void setLightsStatesInputShouldRejectNullElement() {
    final List<LightsState> lightsStates = Collections.singletonList(null);

    assertThrows(NullPointerException.class, () -> new SetLightsStatesInput(lightsStates, STATE, false));
  }

  @Test
  void setLightsStatesInputShouldAllowNullDefaultState() {
    final var lightsState = LightsState.of(Selectors.byId("aaaaa"), STATE);

    assertNotNull(new SetLightsStatesInput(List.of(lightsState), null, false));
  }

  @Test
  void setLightsStatesInputShouldCopyList() {
    final List<LightsState> lightsStates = new ArrayList<>();
    lightsStates.add(LightsState.of(Selectors.byId("aaaaa"), STATE));

    final var input = new SetLightsStatesInput(lightsStates, null, false);
    lightsStates.clear();

    assertEquals(1, input.getLightsStates().size());
  }
}
