package com.github.gilbertotcc.lifx.models;

import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.gilbertotcc.lifx.models.deserializer.ResultStatusDeserializer;
import io.vavr.collection.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

  @AllArgsConstructor
  public enum Status {

    OK("ok"),
    TIMED_OUT("timed_out"),
    OFFLINE("offline");

    public final String label;

    public static Status byLabel(final String label) {
      return Stream.of(values())
        .find(status -> status.label.equals(label))
        .getOrElseThrow(() -> new IllegalArgumentException(format("Unknown status '%s'", label)));
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
