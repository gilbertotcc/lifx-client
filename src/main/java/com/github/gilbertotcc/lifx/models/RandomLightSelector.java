package com.github.gilbertotcc.lifx.models;

public record RandomLightSelector(RandomizableLightSelector selector) implements LightSelector {

  /**
   * Appends {@code :random} to the base identifier for the API to select a random matching light.
   */
  @Override
  public String identifier() {
    return "%s:random".formatted(selector.identifier());
  }
}
