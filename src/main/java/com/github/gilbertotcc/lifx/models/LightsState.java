package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.LightSelectorSerializer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LightsState extends AbstractState {

  @JsonProperty("selector")
  @JsonSerialize(using = LightSelectorSerializer.class)
  private LightSelector lightSelector;

  @Builder
  public LightsState(final LightSelector lightSelector,
                     final Power power,
                     final String color,
                     final Double brightness,
                     final Duration duration,
                     final Double infrared,
                     final Boolean fast) {
    super(power, color, brightness, duration, infrared, fast);
    this.lightSelector = lightSelector;
  }

  public static LightsState of(final LightSelector lightSelector, final State state) {
    return LightsState.builder()
      .lightSelector(lightSelector)
      .power(state.getPower())
      .color(state.getColor())
      .brightness(state.getBrightness())
      .duration(state.getDuration())
      .infrared(state.getInfrared())
      .fast(state.getFast())
      .build();
  }
}
