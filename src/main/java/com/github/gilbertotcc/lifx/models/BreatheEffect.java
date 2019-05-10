package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BreatheEffect extends AbstractVariableEffect {

  @JsonProperty("peak")
  Double peak;

  @Builder
  BreatheEffect(final String color,
                final String fromColor,
                final Duration period,
                final Double cycles,
                final Boolean persistEffect,
                final Boolean powerOn,
                final Double peak) {
    super(color, fromColor, period, cycles, persistEffect, powerOn);
    this.peak = peak;
  }
}
