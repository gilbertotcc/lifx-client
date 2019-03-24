package com.github.gilbertotcc.lifx.models;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Builder
@Getter
public class CombinedLightSelector implements LightSelector, CombinableLightSelector {

  @Singular
  private Set<CombinableLightSelector> selectors;

  @Override
  public String getIdentifier() {
    return selectors.stream()
      .map(CombinableLightSelector::getIdentifier)
      .collect(Collectors.joining(","));
  }

  @Override
  public CombinedLightSelector and(final CombinableLightSelector lightSelector) {
    return CombinedLightSelector.builder()
      .selectors(selectors)
      .selector(lightSelector)
      .build();
  }
}
