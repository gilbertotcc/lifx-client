package com.github.gilbertotcc.lifx.models;

public sealed interface CombinableLightSelector extends LightSelector
  permits CombinedLightSelector,
          MultiZoneLightSelector,
          MultiZoneRangeLightSelector,
          Selectors.GroupIdSelector,
          Selectors.GroupSelector,
          Selectors.IdSelector,
          Selectors.LabelSelector,
          Selectors.LocationIdSelector,
          Selectors.LocationSelector,
          Selectors.SceneIdSelector {

  default CombinedLightSelector and(CombinableLightSelector lightSelector) {
    return CombinedLightSelector.builder()
      .selector(this)
      .selector(lightSelector)
      .build();
  }
}
