package com.github.gilbertotcc.lifx.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;

public class StateDelta {

    @JsonProperty("power")
    @JsonSerialize(using = PowerSerializer.class)
    private Power power;

    @JsonProperty("duration")
    private Double duration;

    @JsonProperty("infrared")
    private Double infrared;

    @JsonProperty("hue")
    private Double hue;

    @JsonProperty("saturation")
    private Double saturation;

    @JsonProperty("brightness")
    private Double brightness;

    @JsonProperty("kelvin")
    private Double kelvin;

    private StateDelta() {}

    public static Builder builder() {
        return new Builder();
    }

    public Power getPower() {
        return power;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getInfrared() {
        return infrared;
    }

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


    public static final class Builder {
        private Power power;
        private Double duration;
        private Double infrared;
        private Double hue;
        private Double saturation;
        private Double brightness;
        private Double kelvin;

        private Builder() {}

        public Builder power(Power power) {
            this.power = power;
            return this;
        }

        public Builder duration(Double duration) {
            this.duration = duration;
            return this;
        }

        public Builder infrared(Double infrared) {
            this.infrared = infrared;
            return this;
        }

        public Builder hue(Double hue) {
            this.hue = hue;
            return this;
        }

        public Builder saturation(Double saturation) {
            this.saturation = saturation;
            return this;
        }

        public Builder brightness(Double brightness) {
            this.brightness = brightness;
            return this;
        }

        public Builder kelvin(Double kelvin) {
            this.kelvin = kelvin;
            return this;
        }

        public StateDelta build() {
            StateDelta stateDelta = new StateDelta();
            stateDelta.infrared = this.infrared;
            stateDelta.power = this.power;
            stateDelta.hue = this.hue;
            stateDelta.brightness = this.brightness;
            stateDelta.saturation = this.saturation;
            stateDelta.kelvin = this.kelvin;
            stateDelta.duration = this.duration;
            return stateDelta;
        }
    }
}
