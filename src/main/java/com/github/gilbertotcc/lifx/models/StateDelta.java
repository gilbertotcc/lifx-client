package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.jspecify.annotations.Nullable;

@Builder
public record StateDelta(
  @JsonProperty("power")
  @Nullable Power power,

  @JsonProperty("duration")
  @Nullable Duration duration,

  @JsonProperty("infrared")
  @Nullable Double infrared,

  @JsonProperty("hue")
  @Nullable Double hue,

  @JsonProperty("saturation")
  @Nullable Double saturation,

  @JsonProperty("brightness")
  @Nullable Double brightness,

  @JsonProperty("kelvin")
  @Nullable Double kelvin
) {}
