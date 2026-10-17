package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

@Getter
@ToString
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
abstract class AbstractState {

  @JsonProperty("power")
  @JsonSerialize(using = PowerSerializer.class)
  private @Nullable Power power;

  @JsonProperty("color")
  private @Nullable String color;

  @JsonProperty("brightness")
  private @Nullable Double brightness;

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  private @Nullable Duration duration;

  @JsonProperty("infrared")
  private @Nullable Double infrared;

  @JsonProperty("fast")
  private @Nullable Boolean fast;
}
