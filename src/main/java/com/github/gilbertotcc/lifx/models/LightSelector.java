package com.github.gilbertotcc.lifx.models;

public sealed interface LightSelector
  permits CombinableLightSelector,
          MultiZoneEnabledLightSelector,
          RandomizableLightSelector,
          RandomLightSelector {

  String identifier();
}
