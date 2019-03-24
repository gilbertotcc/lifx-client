package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MultiZoneRangeLightSelector implements LightSelector, CombinableLightSelector {

  private MultiZoneEnabledLightSelector selector;

  private int rangeStart;
  private int rangeEnd;

  @Override
  public String identifier() {
    return format("%s|%d-%d", selector.identifier(), rangeStart, rangeEnd);
  }
}
