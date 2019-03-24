package com.github.gilbertotcc.lifx.models;

public interface RandomizableLightSelector extends LightSelector {

  default RandomLightSelector random() {
    return new RandomLightSelector(this);
  }
}
