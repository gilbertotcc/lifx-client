package com.github.gilbertotcc.lifx.models;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Singular;

@Builder
public record CombinedLightSelector(@Singular Set<CombinableLightSelector> selectors)
  implements LightSelector, CombinableLightSelector {

  @Override
  public String identifier() {
    return selectors.stream()
      .map(CombinableLightSelector::identifier)
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
