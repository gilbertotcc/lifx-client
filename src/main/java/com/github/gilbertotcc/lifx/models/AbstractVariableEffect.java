package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

@Getter
@ToString
@AllArgsConstructor
abstract class AbstractVariableEffect {

  @JsonProperty("color")
  private @Nullable String color;

  @JsonProperty("from_color")
  private @Nullable String fromColor;

  @JsonProperty("period")
  @JsonSerialize(using = DurationSerializer.class)
  private @Nullable Duration period;

  @JsonProperty("cycles")
  private @Nullable Double cycles;

  @JsonProperty("persist")
  @Nullable Boolean persistEffect;

  @JsonProperty("power_on")
  @Nullable Boolean powerOn;
}
