package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Color {

    @JsonProperty("hue")
    private Double hue;

    @JsonProperty("saturation")
    private Double saturation;

    @JsonProperty("brightness")
    private Double brightness;

    @JsonProperty("kelvin")
    private Double kelvin;

    private Color() {}

    public Double getHue() {
        return hue;
    }

    public Double getSaturation() {
        return saturation;
    }

    public Double getBrightness() {
        return brightness;
    }

    public Double getKelvin() {
        return kelvin;
    }
}
