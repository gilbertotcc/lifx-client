package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Light;

import java.util.List;

public record ListLightsOutput(List<Light> lights) {

  /**
   * Creates output with an unmodifiable snapshot of the supplied light list.
   *
   * @throws NullPointerException if the list or any of its elements is null
   */
  public ListLightsOutput {
    lights = List.copyOf(lights);
  }
}
