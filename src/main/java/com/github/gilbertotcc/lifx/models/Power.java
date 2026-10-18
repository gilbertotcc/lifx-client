package com.github.gilbertotcc.lifx.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Power {

  ON("on"),
  OFF("off");

  public final String label;

  public static Power byLabel(final String label) {
    return switch (label) {
      case "on"  -> ON;
      case "off" -> OFF;
      default    -> throw new IllegalArgumentException("Unexpected power: %s".formatted(label));
    };
  }
}
