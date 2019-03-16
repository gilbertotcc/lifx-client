package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PulseEffect {

  @JsonProperty("color")
  private String color;

  @JsonProperty("from_color")
  private String fromColor;

  @JsonProperty("period")
  @JsonSerialize(using = DurationSerializer.class)
  private Duration period;

  @JsonProperty("cycles")
  private Double cycles;

  @JsonProperty("persist")
  Boolean persistEffect;

  @JsonProperty("power_on")
  Boolean powerOn;
}
