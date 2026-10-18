package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.Builder;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

@Builder
public record StateDelta(
  @JsonProperty("power")
  @JsonSerialize(using = PowerSerializer.class)
  @Nullable Power power,

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
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
