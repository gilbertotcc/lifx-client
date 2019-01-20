package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;

public class TogglePower {

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  private Duration duration;

  private TogglePower(final Duration duration) {
    this.duration = duration;
  }

  public static TogglePower in(final Duration duration) {
    return new TogglePower(duration);
  }

  public Duration getDuration() {
    return duration;
  }
}
