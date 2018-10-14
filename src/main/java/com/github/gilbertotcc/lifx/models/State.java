package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class State {

    @JsonProperty("power")
    @JsonSerialize(using = PowerSerializer.class)
    private Power power;

    @JsonProperty("color")
    private String color;

    @JsonProperty("brightness")
    private Double brightness;

    @JsonProperty("duration")
    private Double duration;

    @JsonProperty("infrared")
    private Double infrared;

    @JsonProperty("fast")
    private Boolean fast;

    private State(final Power power, final String color, final Double brightness, final Double duration, final Double infrared, final Boolean fast) {
        this.power = power;
        this.color = color;
        this.brightness = brightness;
        this.duration = duration;
        this.infrared = infrared;
        this.fast = fast;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Power getPower() {
        return power;
    }

    public String getColor() {
        return color;
    }

    public Double getBrightness() {
        return brightness;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getInfrared() {
        return infrared;
    }

    public Boolean getFast() {
        return fast;
    }

    public static class Builder {

        private Power power;
        private String color;
        private Double brightness;
        private Double duration;
        private Double infrared;
        private Boolean fast;

        private Builder() {}

        public Builder power(final Power power) {
            this.power = power;
            return this;
        }

        public Builder color(final String color) {
            this.color = color;
            return this;
        }

        public Builder brightness(final Double brightness) {
            this.brightness = brightness;
            return this;
        }

        public Builder duration(final Double duration) {
            this.duration = duration;
            return this;
        }

        public Builder infrared(final Double infrared) {
            this.infrared = infrared;
            return this;
        }

        public Builder fast(final Boolean fast) {
            this.fast = fast;
            return this;
        }

        public State build() {
            return new State(power, color, brightness, duration, infrared, fast);
        }
    }
}
