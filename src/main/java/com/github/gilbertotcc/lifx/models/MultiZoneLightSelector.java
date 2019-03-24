package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiZoneLightSelector implements LightSelector, CombinableLightSelector {

  private MultiZoneEnabledLightSelector selector;

  private Set<Integer> zones;

  @Override
  public String getIdentifier() {
    return zones.stream()
      .map(zone -> zone.toString())
      .reduce(selector.getIdentifier(), (multiZoneSelector, zone) -> format("%s|%s", multiZoneSelector, zone));
  }
}
