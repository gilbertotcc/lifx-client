package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;

import java.util.Objects;

public record TransitToNextStateInput(LightSelector lightSelector, Cycle cycle) {

  /**
   * Creates the input of a transition to the next state of a cycle.
   *
   * @throws NullPointerException if a component is null
   */
  public TransitToNextStateInput {
    Objects.requireNonNull(lightSelector, "lightSelector");
    Objects.requireNonNull(cycle, "cycle");
  }
}
