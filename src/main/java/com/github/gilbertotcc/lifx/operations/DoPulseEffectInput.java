package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.PulseEffect;

import java.util.Objects;

public record DoPulseEffectInput(LightSelector lightSelector, PulseEffect pulseEffect) {

  /**
   * Creates the input of a pulse effect.
   *
   * @throws NullPointerException if a component is null
   */
  public DoPulseEffectInput {
    Objects.requireNonNull(lightSelector, "lightSelector");
    Objects.requireNonNull(pulseEffect, "pulseEffect");
  }
}
