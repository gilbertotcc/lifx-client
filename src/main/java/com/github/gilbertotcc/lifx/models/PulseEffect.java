package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PulseEffect extends AbstractVariableEffect {

  @Builder
  public PulseEffect(final String color,
                     final String fromColor,
                     final Duration period,
                     final Double cycles,
                     final Boolean persistEffect,
                     final Boolean powerOn) {
    super(color, fromColor, period, cycles, persistEffect, powerOn);
  }
}
