package com.github.gilbertotcc.lifx.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiZoneRangeLightSelector implements LightSelector, CombinableLightSelector {

  private MultiZoneEnabledLightSelector selector;

  private int rangeStart;
  private int rangeEnd;

  @Override
  public String identifier() {
    return "%s|%d-%d".formatted(selector.identifier(), rangeStart, rangeEnd);
  }
}
