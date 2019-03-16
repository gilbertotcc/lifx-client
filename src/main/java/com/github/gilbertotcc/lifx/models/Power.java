package com.github.gilbertotcc.lifx.models;

import java.util.stream.Stream;

public enum Power {

  ON("on"),
  OFF("off");

  public final String label;

  Power(final String label) {
    this.label = label;
  }

  public static Power byLabel(final String label) {
    return Stream.of(values()).filter(power -> power.label.equals(label)).findFirst()
      .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown power '%s'", label)));
  }
}
