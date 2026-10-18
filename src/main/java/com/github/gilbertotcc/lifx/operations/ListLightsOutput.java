package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Light;

import java.util.List;

public record ListLightsOutput(List<Light> lights) {

  public ListLightsOutput {
    lights = List.copyOf(lights);
  }
}
