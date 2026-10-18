package com.github.gilbertotcc.lifx.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RandomLightSelector implements LightSelector {

  private RandomizableLightSelector selector;

  @Override
  public String identifier() {
    return "%s:random".formatted(selector.identifier());
  }
}
