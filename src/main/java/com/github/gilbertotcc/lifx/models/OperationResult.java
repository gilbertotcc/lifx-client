package com.github.gilbertotcc.lifx.models;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationResult {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Operation {

    @JsonProperty("selector")
    private @Nullable String selector;
  }

  @JsonProperty("operation")
  private @Nullable Operation operation;

  @JsonProperty("results")
  private List<Result> results = Collections.emptyList();

  @JsonProperty("error")
  private @Nullable String error;
}
