package com.github.gilbertotcc.lifx.models;

import java.util.Set;

public record MultiZoneLightSelector(MultiZoneEnabledLightSelector selector, Set<Integer> zones)
  implements LightSelector, CombinableLightSelector {

  @Override
  public String identifier() {
    return zones.stream()
      .map(Object::toString)
      .reduce(selector.identifier(), "%s|%s"::formatted);
  }
}
