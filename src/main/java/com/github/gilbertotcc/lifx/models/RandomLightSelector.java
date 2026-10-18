package com.github.gilbertotcc.lifx.models;

public record RandomLightSelector(RandomizableLightSelector selector) implements LightSelector {

  @Override
  public String identifier() {
    return "%s:random".formatted(selector.identifier());
  }
}
