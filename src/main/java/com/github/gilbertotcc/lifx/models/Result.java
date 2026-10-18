package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.deserializer.ResultStatusDeserializer;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonDeserialize;

public record Result(
  @JsonProperty("id")
  @Nullable String id,

  @JsonProperty("label")
  @Nullable String label,

  @JsonProperty("status")
  @JsonDeserialize(using = ResultStatusDeserializer.class)
  @Nullable Status status
) {

  @AllArgsConstructor
  public enum Status {

    OK("ok"),
    TIMED_OUT("timed_out"),
    OFFLINE("offline");

    public final String label;

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
