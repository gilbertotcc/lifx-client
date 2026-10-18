package com.github.gilbertotcc.lifx.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
// Renaming the class to avoid the clash with java.lang.Error would break the public API
@SuppressWarnings("AvoidCommonTypeNames")
public record Error(
  @JsonProperty("error")
  @Nullable String errorMessage,

  // List is not Serializable, but the values used (Jackson's ArrayList, List.of()) are
  @SuppressWarnings("serial")
  @JsonProperty("errors")
  List<ErrorDetail> details
) implements Serializable {

  @Serial
  private static final long serialVersionUID = -790843377907574526L;

  /**
   * Creates a response value, replacing a null details list with an empty list.
   * A non-null list is retained without copying.
   */
  public Error {
    details = Objects.requireNonNullElse(details, List.of());
  }

  public record ErrorDetail(
    @JsonProperty("field")
    @Nullable String field,

    // List is not Serializable, but Jackson always fills it with an ArrayList
    @SuppressWarnings("serial")
    @JsonProperty("message")
    @Nullable List<String> messages
  ) implements Serializable {

    @Serial
    private static final long serialVersionUID = -7071300361893825402L;
  }
}
