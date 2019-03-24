package com.github.gilbertotcc.lifx.models;

public interface CombinableLightSelector extends LightSelector {

  default CombinedLightSelector and(CombinableLightSelector lightSelector) {
    return CombinedLightSelector.builder()
      .selector(this)
      .selector(lightSelector)
      .build();
  }
}
