package com.github.gilbertotcc.lifx.models;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;

public sealed interface MultiZoneEnabledLightSelector extends LightSelector
  permits Selectors.AllSelector,
          Selectors.GroupIdSelector,
          Selectors.GroupSelector,
          Selectors.IdSelector,
          Selectors.LabelSelector,
          Selectors.LocationIdSelector,
          Selectors.LocationSelector,
          Selectors.SceneIdSelector {

  default MultiZoneLightSelector onZones(int ... zones) {
    return new MultiZoneLightSelector(this, Arrays.stream(zones).boxed().collect(toSet()));
  }

  default MultiZoneRangeLightSelector onZonesRange(int rangeStart, int rangeEnd) {
    return new MultiZoneRangeLightSelector(this, rangeStart, rangeEnd);
  }
}
