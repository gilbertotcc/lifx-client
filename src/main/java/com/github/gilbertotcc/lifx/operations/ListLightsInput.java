package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.Selectors;

public record ListLightsInput(LightSelector selector) {
  public static final ListLightsInput ALL_LIGHTS = new ListLightsInput(Selectors.all());
}
