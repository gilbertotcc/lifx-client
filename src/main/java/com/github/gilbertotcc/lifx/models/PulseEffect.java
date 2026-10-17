package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

@Getter
@ToString(callSuper = true)
public class PulseEffect extends AbstractVariableEffect {

  /**
   * Creates pulse-effect settings without local validation or defaults.
   * Null settings are omitted by the client's JSON mapper; the period is serialized in whole seconds.
   */
  @Builder
  public PulseEffect(final @Nullable String color,
                     final @Nullable String fromColor,
                     final @Nullable Duration period,
                     final @Nullable Double cycles,
                     final @Nullable Boolean persistEffect,
                     final @Nullable Boolean powerOn) {
    super(color, fromColor, period, cycles, persistEffect, powerOn);
  }
}
