package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

@Getter
@ToString
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
abstract class AbstractState {

  @JsonProperty("power")
  private @Nullable Power power;

  @JsonProperty("color")
  private @Nullable String color;

  @JsonProperty("brightness")
  private @Nullable Double brightness;

  @JsonProperty("duration")
  private @Nullable Duration duration;

  @JsonProperty("infrared")
  private @Nullable Double infrared;

  @JsonProperty("fast")
  private @Nullable Boolean fast;
}
