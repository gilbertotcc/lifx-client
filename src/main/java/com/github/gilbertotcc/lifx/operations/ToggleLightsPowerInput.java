package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;

import java.time.Duration;
import java.util.Objects;

public record ToggleLightsPowerInput(LightSelector lightSelector, Duration duration) {

  /**
   * Creates the input of a power toggle.
   *
   * @throws NullPointerException if a component is null
   */
  public ToggleLightsPowerInput {
    Objects.requireNonNull(lightSelector, "lightSelector");
    Objects.requireNonNull(duration, "duration");
  }
}
