package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiZoneLightSelector implements LightSelector, CombinableLightSelector {

  private MultiZoneEnabledLightSelector selector;

  private Set<Integer> zones;

  @Override
  public String identifier() {
    return zones.stream()
      .map(Object::toString)
      .reduce(selector.identifier(), (multiZoneSelector, zone) -> format("%s|%s", multiZoneSelector, zone));
  }
}
