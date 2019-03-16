package com.github.gilbertotcc.lifx.models;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.gilbertotcc.lifx.models.deserializer.ResultStatusDeserializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

  public enum Status {

    OK("ok"),
    TIMED_OUT("timed_out"),
    OFFLINE("offline");

    public final String label;

    Status(String label) {
      this.label = label;
    }

    public static Status byLabel(final String label) {
      return Stream.of(values()).filter(status -> status.label.equals(label)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown status '%s'", label)));
    }
  }

  @JsonProperty("id")
  private String id;

  @JsonProperty("label")
  private String label;

  @JsonProperty("status")
  @JsonDeserialize(using = ResultStatusDeserializer.class)
  private Status status;
}
