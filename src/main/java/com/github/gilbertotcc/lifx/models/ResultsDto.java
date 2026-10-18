package com.github.gilbertotcc.lifx.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultsDto<T>(
  @JsonProperty("results")
  List<T> results
) {

  public ResultsDto {
    results = Objects.requireNonNullElse(results, List.of());
  }
}
