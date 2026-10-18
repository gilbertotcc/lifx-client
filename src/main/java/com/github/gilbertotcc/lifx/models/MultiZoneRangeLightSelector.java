package com.github.gilbertotcc.lifx.models;

public record MultiZoneRangeLightSelector(MultiZoneEnabledLightSelector selector, int rangeStart, int rangeEnd)
  implements LightSelector, CombinableLightSelector {

  @Override
  public String identifier() {
    return "%s|%d-%d".formatted(selector.identifier(), rangeStart, rangeEnd);
  }
}
