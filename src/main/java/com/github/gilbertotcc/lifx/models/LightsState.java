package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.LightSelectorSerializer;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

@Getter
public class LightsState extends AbstractState {

  @JsonProperty("selector")
  @JsonSerialize(using = LightSelectorSerializer.class)
  private LightSelector lightSelector;

  @Builder
  public LightsState(final LightSelector lightSelector,
                     final @Nullable Power power,
                     final @Nullable String color,
                     final @Nullable Double brightness,
                     final @Nullable Duration duration,
                     final @Nullable Double infrared,
                     final @Nullable Boolean fast) {
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
