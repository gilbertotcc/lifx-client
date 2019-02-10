package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class State {

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
}
