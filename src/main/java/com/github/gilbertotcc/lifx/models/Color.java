package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.jspecify.annotations.Nullable;

public record Color(
  @JsonProperty("hue")
  @Nullable Double hue,

  @JsonProperty("saturation")
  @Nullable Double saturation,

  @JsonProperty("brightness")
  @Nullable Double brightness,

  @JsonProperty("kelvin")
  @Nullable Double kelvin
) {}
