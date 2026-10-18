package com.github.gilbertotcc.lifx.models;

public record MultiZoneRangeLightSelector(MultiZoneEnabledLightSelector selector, int rangeStart, int rangeEnd)
  implements CombinableLightSelector {

  /**
   * Renders the base selector followed by {@code |rangeStart-rangeEnd}, without validating the bounds.
   */
  @Override
  public String identifier() {
    return "%s|%d-%d".formatted(selector.identifier(), rangeStart, rangeEnd);
  }
}
