package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

@Getter
@ToString(callSuper = true)
public class BreatheEffect extends AbstractVariableEffect {

  @JsonProperty("peak")
  @Nullable Double peak;

  /**
   * Creates breathe-effect settings without local validation or defaults.
   * Null settings are omitted by the client's JSON mapper; the period is serialized in whole seconds.
   */
  @Builder
  BreatheEffect(final @Nullable String color,
                final @Nullable String fromColor,
                final @Nullable Duration period,
                final @Nullable Double cycles,
                final @Nullable Boolean persistEffect,
                final @Nullable Boolean powerOn,
                final @Nullable Double peak) {
    super(color, fromColor, period, cycles, persistEffect, powerOn);
    this.peak = peak;
  }
}
