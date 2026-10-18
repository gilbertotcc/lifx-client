package com.github.gilbertotcc.lifx.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.deserializer.PowerDeserializer;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Light(
  @JsonProperty("id")
  @Nullable String id,

  @JsonProperty("uuid")
  @Nullable String uuid,

  @JsonProperty("label")
  @Nullable String label,

  @JsonProperty("connected")
  boolean connected,

  @JsonProperty("power")
  @JsonDeserialize(using = PowerDeserializer.class)
  @Nullable Power power,

  @JsonProperty("color")
  @Nullable Color color,

  @JsonProperty("infrared")
  @Nullable String infrared,

  @JsonProperty("brightness")
  double brightness,

  @JsonProperty("group")
  @Nullable Group group,

  @JsonProperty("location")
  @Nullable Location location,

  @JsonProperty("last_seen")
  @Nullable ZonedDateTime lastSeen,

  @JsonProperty("seconds_since_seen")
  int secondsSinceSeen,

  @JsonProperty("product")
  @Nullable Product product
) {

  public record Color(
    @JsonProperty("hue")
    double hue,

    @JsonProperty("saturation")
    double saturation,

    @JsonProperty("kelvin")
    int kelvin
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
      boolean hasColor,

      @JsonProperty("has_variable_color_temp")
      boolean hasVariableColorTemperature,

      @JsonProperty("min_kelvin")
      int minKelvin,

      @JsonProperty("max_kelvin")
      int maxKelvin,

      @JsonProperty("has_ir")
      boolean hasIr,

      @JsonProperty("has_chain")
      boolean hasChain,

      @JsonProperty("has_multizone")
      boolean hasMultizone
    ) {
    }
  }
}
