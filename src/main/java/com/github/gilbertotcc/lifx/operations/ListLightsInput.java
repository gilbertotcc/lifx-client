package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.Selectors;

import java.util.Objects;

public record ListLightsInput(LightSelector selector) {
  public static final ListLightsInput ALL_LIGHTS = new ListLightsInput(Selectors.all());

  /**
   * Creates the input of a light listing.
   *
   * @throws NullPointerException if the selector is null
   */
  public ListLightsInput {
    Objects.requireNonNull(selector, "selector");
  }
}
