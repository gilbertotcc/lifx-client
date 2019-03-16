package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StateDelta {

  @JsonProperty("power")
  @JsonSerialize(using = PowerSerializer.class)
  private Power power;

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  private Duration duration;

  @JsonProperty("infrared")
  private Double infrared;

  @JsonProperty("hue")
  private Double hue;

  @JsonProperty("saturation")
  private Double saturation;

  @JsonProperty("brightness")
  private Double brightness;

  @JsonProperty("kelvin")
  private Double kelvin;
}
