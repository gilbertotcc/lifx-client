package com.github.gilbertotcc.lifx.models;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationResult {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Operation {

    @JsonProperty("selector")
    private String selector;
  }

  @JsonProperty("operation")
  private Operation operation;

  @JsonProperty("results")
  private List<Result> results = Collections.emptyList();

  @JsonProperty("error")
  private String error;
}
