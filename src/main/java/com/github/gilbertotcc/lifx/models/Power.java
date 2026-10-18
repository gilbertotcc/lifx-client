package com.github.gilbertotcc.lifx.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Power {

  ON("on"),
  OFF("off");

  public final String label;

  /**
   * Resolves an exact, case-sensitive power label without trimming whitespace.
   *
   * @param label {@code "on" or "off"}
   * @return the matching power
   * @throws IllegalArgumentException if the label is unrecognized
   * @throws NullPointerException if the label is null
   */
  public static Power byLabel(final String label) {
    return switch (label) {
      case "on"  -> ON;
      case "off" -> OFF;
      default    -> throw new IllegalArgumentException("Unexpected power: %s".formatted(label));
    };
  }
}
