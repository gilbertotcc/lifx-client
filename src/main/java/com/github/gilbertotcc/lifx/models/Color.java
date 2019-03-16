package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Color {

  @JsonProperty("hue")
  private Double hue;

  @JsonProperty("saturation")
  private Double saturation;

  @JsonProperty("brightness")
  private Double brightness;

  @JsonProperty("kelvin")
  private Double kelvin;
}
