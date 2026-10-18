package com.github.gilbertotcc.lifx.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OperationResult(
  @JsonProperty("operation")
  @Nullable Operation operation,

  @JsonProperty("results")
  List<Result> results,

  @JsonProperty("error")
  @Nullable String error
) {

  public OperationResult {
    results = Objects.requireNonNullElse(results, List.of());
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Operation(
    @JsonProperty("selector")
    @Nullable String selector
  ) {
  }
}
