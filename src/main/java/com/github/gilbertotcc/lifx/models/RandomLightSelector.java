package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RandomLightSelector implements LightSelector {

  private RandomizableLightSelector selector;

  @Override
  public String getIdentifier() {
    return format("%s:random", selector.getIdentifier());
  }
}
