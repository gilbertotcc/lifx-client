package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.State;

import java.util.Objects;

public record SetLightsStateInput(LightSelector lightSelector, State state) {

  /**
   * Creates the input of a state update.
   *
   * @throws NullPointerException if a component is null
   */
  public SetLightsStateInput {
    Objects.requireNonNull(lightSelector, "lightSelector");
    Objects.requireNonNull(state, "state");
  }
}
