package com.github.gilbertotcc.lifx.models;

import java.util.Set;

public record MultiZoneLightSelector(MultiZoneEnabledLightSelector selector, Set<Integer> zones)
  implements CombinableLightSelector {

  /**
   * Renders the base selector followed by {@code |zone} for each zone in the set's iteration order.
   * An empty set yields the base identifier alone.
   */
  @Override
  public String identifier() {
    return zones.stream()
      .map(Object::toString)
      .reduce(selector.identifier(), "%s|%s"::formatted);
  }
}
