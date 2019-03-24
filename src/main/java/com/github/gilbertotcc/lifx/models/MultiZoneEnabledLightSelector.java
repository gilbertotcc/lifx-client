package com.github.gilbertotcc.lifx.models;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;

public interface MultiZoneEnabledLightSelector extends LightSelector {

  default MultiZoneLightSelector onZones(int ... zones) {
    return new MultiZoneLightSelector(this, Arrays.stream(zones).boxed().collect(toSet()));
  }

  default MultiZoneRangeLightSelector onZonesRange(int rangeStart, int rangeEnd) {
    return new MultiZoneRangeLightSelector(this, rangeStart, rangeEnd);
  }
}
