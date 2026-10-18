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

  /**
   * Creates power-toggle settings with the requested transition duration.
   *
   * @param duration transition duration, serialized in whole seconds with the nanosecond component ignored
   */
  public static TogglePower in(final Duration duration) {
    return new TogglePower(duration);
  }
}
