package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import tools.jackson.databind.annotation.JsonSerialize;

public record TogglePower(
  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  Duration duration
) {

  public static TogglePower in(final Duration duration) {
    return new TogglePower(duration);
  }
}
