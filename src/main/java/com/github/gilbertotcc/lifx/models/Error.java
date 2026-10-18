package com.github.gilbertotcc.lifx.models;

import java.io.Serial;
import java.io.Serializable;
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
// Renaming the class to avoid the clash with java.lang.Error would break the public API
@SuppressWarnings("AvoidCommonTypeNames")
public class Error implements Serializable {

  @Serial
  private static final long serialVersionUID = -790843377907574526L;

  @Getter
  @NoArgsConstructor
  public static class ErrorDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -7071300361893825402L;

    @JsonProperty("field")
    private @Nullable String field;

    // List is not Serializable, but Jackson always fills it with an ArrayList
    @SuppressWarnings("serial")
    @JsonProperty("message")
    private @Nullable List<String> messages;
  }

  @JsonProperty("error")
  private @Nullable String errorMessage;

  // List is not Serializable, but the values used (Jackson's ArrayList, Collections.emptyList()) are
  @SuppressWarnings("serial")
  @JsonProperty("errors")
  private List<ErrorDetail> details  = Collections.emptyList();
}
