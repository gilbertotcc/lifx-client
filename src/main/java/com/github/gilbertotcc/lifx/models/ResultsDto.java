package com.github.gilbertotcc.lifx.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultsDto<T>(
  @JsonProperty("results")
  List<T> results
) {

  /**
   * Creates a response value, replacing a null results list with an empty list.
   * A non-null list is retained without copying.
   */
  public ResultsDto {
    results = Objects.requireNonNullElse(results, List.of());
  }
}
