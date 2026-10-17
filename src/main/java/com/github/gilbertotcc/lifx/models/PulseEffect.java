package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

@Getter
@ToString(callSuper = true)
public class PulseEffect extends AbstractVariableEffect {

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
