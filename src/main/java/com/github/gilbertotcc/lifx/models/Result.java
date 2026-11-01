package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;

public record Result(
  @JsonProperty("id")
  @Nullable String id,

  @JsonProperty("label")
  @Nullable String label,

  @JsonProperty("status")
  @Nullable Status status
) {

  @AllArgsConstructor
  public enum Status {

    OK("ok"),
    TIMED_OUT("timed_out"),
    OFFLINE("offline");

    public final String label;

    /**
     * Resolves an exact, case-sensitive result status label without trimming whitespace.
     *
     * @param label {@code "ok", "timed_out", or "offline"}
     * @return the matching result status
     * @throws IllegalArgumentException if the label is unrecognized
     * @throws NullPointerException if the label is null
     */
    public static Status byLabel(final String label) {
      return switch (label) {
        case "ok"        -> OK;
        case "timed_out" -> TIMED_OUT;
        case "offline"   -> OFFLINE;
        default          -> throw new IllegalArgumentException("Unknown status '%s'".formatted(label));
      };
    }
  }
}
