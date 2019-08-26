package com.github.gilbertotcc.lifx.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

import static java.lang.String.format;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Power {

  ON("on"),
  OFF("off");

  public final String label;

  public static Power byLabel(final String label) {
    return Stream.of(values()).filter(power -> power.label.equals(label)).findFirst()
      .orElseThrow(() -> new IllegalArgumentException(format("Unknown power '%s'", label)));
  }
}
