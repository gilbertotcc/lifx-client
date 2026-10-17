package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

@Getter
@ToString
@Builder
public class StateDelta {

  @JsonProperty("power")
  @JsonSerialize(using = PowerSerializer.class)
  private @Nullable Power power;

  @JsonProperty("duration")
  @JsonSerialize(using = DurationSerializer.class)
  private @Nullable Duration duration;

  @JsonProperty("infrared")
  private @Nullable Double infrared;

  @JsonProperty("hue")
  private @Nullable Double hue;

  @JsonProperty("saturation")
  private @Nullable Double saturation;

  @JsonProperty("brightness")
  private @Nullable Double brightness;

  @JsonProperty("kelvin")
  private @Nullable Double kelvin;
}
