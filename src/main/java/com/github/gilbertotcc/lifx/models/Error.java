package com.github.gilbertotcc.lifx.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

  @Getter
  @NoArgsConstructor
  public static class ErrorDetail {

    @JsonProperty("field")
    private String field;

    @JsonProperty("message")
    private List<String> messages;
  }

  @JsonProperty("error")
  private String errorMessage;

  @JsonProperty("errors")
  private List<ErrorDetail> details;
}
