package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tools.jackson.databind.annotation.JsonSerialize;

@Getter
@AllArgsConstructor(staticName = "in")
public class TogglePower {

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  private Duration duration;
}
