package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Color {

  @JsonProperty("hue")
  private @Nullable Double hue;

  @JsonProperty("saturation")
  private @Nullable Double saturation;

  @JsonProperty("brightness")
  private @Nullable Double brightness;

  @JsonProperty("kelvin")
  private @Nullable Double kelvin;
}
