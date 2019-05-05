package com.github.gilbertotcc.lifx.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.gilbertotcc.lifx.models.deserializer.PowerDeserializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Light {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Color {

    @JsonProperty("hue")
    private double hue;

    @JsonProperty("saturation")
    private double saturation;

    @JsonProperty("kelvin")
    private int kelvin;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Group {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Location {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
  }

  @Getter
  @NoArgsConstructor
  public static class Product {

    @Getter
    @NoArgsConstructor
    public static class Capabilities {

      @JsonProperty("has_color")
      private boolean hasColor;

      @JsonProperty("has_variable_color_temp")
      private boolean hasVariableColorTemperature;

      @JsonProperty("min_kelvin")
      private int minKelvin;

      @JsonProperty("max_kelvin")
      private int maxKelvin;

      @JsonProperty("has_ir")
      private boolean hasIr;

      @JsonProperty("has_chain")
      private boolean hasChain;

      @JsonProperty("has_multizone")
      private boolean hasMultizone;
    }

    @JsonProperty("name")
    private String name;

    @JsonProperty("company")
    private String company;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("capabilities")
    private Capabilities capabilities;
  }

  @JsonProperty("id")
  private String id;

  @JsonProperty("uuid")
  private String uuid;

  @JsonProperty("label")
  private String label;

  @JsonProperty("connected")
  private boolean connected;

  @JsonProperty("power")
  @JsonDeserialize(using = PowerDeserializer.class)
  private Power power;

  @JsonProperty("color")
  private Color color;

  @JsonProperty("infrared")
  private String infrared;

  @JsonProperty("brightness")
  private double brightness;

  @JsonProperty("group")
  private Group group;

  @JsonProperty("location")
  private Location location;

  @JsonProperty("last_seen")
  private ZonedDateTime lastSeen;

  @JsonProperty("seconds_since_seen")
  private int secondsSinceSeen;

  @JsonProperty("product")
  private Product product;
}
