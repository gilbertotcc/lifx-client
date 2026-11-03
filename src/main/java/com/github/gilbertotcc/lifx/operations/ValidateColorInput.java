package com.github.gilbertotcc.lifx.operations;

import java.util.Objects;

public record ValidateColorInput(String colorString) {

  /**
   * Creates the input of a color validation.
   *
   * @throws NullPointerException if the color string is null
   */
  public ValidateColorInput {
    Objects.requireNonNull(colorString, "colorString");
  }
}
