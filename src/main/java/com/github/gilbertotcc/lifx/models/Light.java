package com.github.gilbertotcc.lifx.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Light(
  @JsonProperty("id")
  @Nullable String id,

  @JsonProperty("uuid")
  @Nullable String uuid,

  @JsonProperty("label")
  @Nullable String label,

  @JsonProperty("connected")
  @Nullable Boolean connected,

  @JsonProperty("power")
  @Nullable Power power,

  @JsonProperty("color")
  @Nullable Color color,

  @JsonProperty("infrared")
  @Nullable String infrared,

  @JsonProperty("brightness")
  @Nullable Double brightness,

  @JsonProperty("group")
  @Nullable Group group,

  @JsonProperty("location")
  @Nullable Location location,

  @JsonProperty("last_seen")
  @Nullable ZonedDateTime lastSeen,

  @JsonProperty("seconds_since_seen")
  @Nullable Double secondsSinceSeen,

  @JsonProperty("product")
  @Nullable Product product
) {

  public record Color(
    @JsonProperty("hue")
    @Nullable Double hue,

    @JsonProperty("saturation")
    @Nullable Double saturation,

    @JsonProperty("kelvin")
    @Nullable Integer kelvin
  ) {
  }

  public record Group(
    @JsonProperty("id")
    @Nullable String id,

    @JsonProperty("name")
    @Nullable String name
  ) {
  }

  public record Location(
    @JsonProperty("id")
    @Nullable String id,

    @JsonProperty("name")
    @Nullable String name
  ) {
  }

  public record Product(
    @JsonProperty("name")
    @Nullable String name,

    @JsonProperty("company")
    @Nullable String company,

    @JsonProperty("identifier")
    @Nullable String identifier,

    @JsonProperty("capabilities")
    @Nullable Capabilities capabilities
  ) {

    public record Capabilities(
      @JsonProperty("has_color")
      @Nullable Boolean hasColor,

      @JsonProperty("has_variable_color_temp")
      @Nullable Boolean hasVariableColorTemperature,

      @JsonProperty("min_kelvin")
      @Nullable Integer minKelvin,

      @JsonProperty("max_kelvin")
      @Nullable Integer maxKelvin,

      @JsonProperty("has_ir")
      @Nullable Boolean hasIr,

      @JsonProperty("has_chain")
      @Nullable Boolean hasChain,

      @JsonProperty("has_multizone")
      @Nullable Boolean hasMultizone
    ) {
    }
  }
}
