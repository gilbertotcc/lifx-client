package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.StateDelta;

import java.util.Objects;

public record SetLightsStateDeltaInput(LightSelector lightSelector, StateDelta stateDelta) {

  /**
   * Creates the input of a state delta update.
   *
   * @throws NullPointerException if a component is null
   */
  public SetLightsStateDeltaInput {
    Objects.requireNonNull(lightSelector, "lightSelector");
    Objects.requireNonNull(stateDelta, "stateDelta");
  }
}
