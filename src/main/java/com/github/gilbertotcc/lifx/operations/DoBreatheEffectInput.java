package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.LightSelector;

import java.util.Objects;

public record DoBreatheEffectInput(LightSelector lightSelector, BreatheEffect breatheEffect) {

  /**
   * Creates the input of a breathe effect.
   *
   * @throws NullPointerException if a component is null
   */
  public DoBreatheEffectInput {
    Objects.requireNonNull(lightSelector, "lightSelector");
    Objects.requireNonNull(breatheEffect, "breatheEffect");
  }
}
