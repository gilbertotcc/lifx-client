package com.github.gilbertotcc.lifx.models;

public sealed interface RandomizableLightSelector extends LightSelector
  permits Selectors.AllSelector,
          Selectors.GroupIdSelector,
          Selectors.GroupSelector,
          Selectors.LocationIdSelector,
          Selectors.LocationSelector,
          Selectors.SceneIdSelector {

  default RandomLightSelector random() {
    return new RandomLightSelector(this);
  }
}
