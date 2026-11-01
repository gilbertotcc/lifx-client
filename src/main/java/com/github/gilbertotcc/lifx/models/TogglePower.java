package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TogglePower(
  @JsonProperty("duration")
  Duration duration
) {

  /**
   * Creates power-toggle settings with the requested transition duration.
   *
   * @param duration transition duration, serialized in seconds, cut to one decimal place
   */
  public static TogglePower in(final Duration duration) {
    return new TogglePower(duration);
  }
}
