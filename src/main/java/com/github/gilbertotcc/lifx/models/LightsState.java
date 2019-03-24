package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.LightsSelectorSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LightsState {

  @JsonProperty("selector")
  @JsonSerialize(using = LightsSelectorSerializer.class)
  private LightSelector lightSelector;

  @JsonProperty("power")
  @JsonSerialize(using = PowerSerializer.class)
  private Power power;

  @JsonProperty("color")
  private String color;

  @JsonProperty("brightness")
  private Double brightness;

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  private Duration duration;

  @JsonProperty("infrared")
  private Double infrared;

  @JsonProperty("fast")
  private Boolean fast;

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
