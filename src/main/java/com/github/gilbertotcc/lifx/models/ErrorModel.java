package com.github.gilbertotcc.lifx.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.jspecify.annotations.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorModel(
  @JsonProperty("error")
  @Nullable String errorMessage,

  @JsonProperty("errors")
  List<ErrorDetail> details
) implements Serializable {

  @Serial
  private static final long serialVersionUID = -790843377907574526L;

  /**
   * Creates a response value, replacing a null details list with an empty list.
   * A non-null list is retained without copying.
   */
  public ErrorModel {
    details = Objects.requireNonNullElse(details, List.of());
  }

  public record ErrorDetail(
    @JsonProperty("field")
    @Nullable String field,

    @JsonProperty("message")
    @Nullable List<String> messages
  ) implements Serializable {

    @Serial
    private static final long serialVersionUID = -7071300361893825402L;

    /**
     * Formats the field and messages as {@code field: [message1, message2]}.
     *
     * @return the formatted detail, with empty brackets when messages are null or empty
     */
    @Override
    public String toString() {
      return "%s: [%s]".formatted(field, messages == null ? "" : String.join(", ", messages));
    }
  }
}
