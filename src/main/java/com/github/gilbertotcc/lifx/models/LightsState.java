package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.LightsSelectorSerializer;

public class LightsState extends State {

  @JsonProperty("selector")
  @JsonSerialize(using = LightsSelectorSerializer.class)
  private LightsSelector lightsSelector;

  private LightsState() {
  }

  private LightsState(final LightsSelector lightsSelector, final State state) {
    super(state);
    this.lightsSelector = lightsSelector;
  }

  public static LightsState of(final LightsSelector lightsSelector, final State state) {
    return new LightsState(lightsSelector, state);
  }

  public LightsSelector getLightsSelector() {
    return lightsSelector;
  }
}
