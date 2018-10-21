package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;

public class PulseEffect {

    @JsonProperty("color")
    private String color;

    @JsonProperty("from_color")
    private String fromColor;

    @JsonProperty("period")
    @JsonSerialize(using = DurationSerializer.class)
    private Duration period;

    @JsonProperty("cycles")
    private Double cycles;

    @JsonProperty("persist")
    Boolean persistEffect;

    @JsonProperty("power_on")
    Boolean powerOn;

    private PulseEffect() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getColor() {
        return color;
    }

    public String getFromColor() {
        return fromColor;
    }

    public Duration getPeriod() {
        return period;
    }

    public Double getCycles() {
        return cycles;
    }

    public Boolean getPersistEffect() {
        return persistEffect;
    }

    public Boolean getPowerOn() {
        return powerOn;
    }

    public static final class Builder {
        Boolean persistEffect;
        Boolean powerOn;
        private String color;
        private String fromColor;
        private Duration period;
        private Double cycles;

        private Builder() {}

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder fromColor(String fromColor) {
            this.fromColor = fromColor;
            return this;
        }

        public Builder period(Duration period) {
            this.period = period;
            return this;
        }

        public Builder cycles(Double cycles) {
            this.cycles = cycles;
            return this;
        }

        public Builder persistEffect(Boolean persistEffect) {
            this.persistEffect = persistEffect;
            return this;
        }

        public Builder powerOn(Boolean powerOn) {
            this.powerOn = powerOn;
            return this;
        }

        public PulseEffect build() {
            PulseEffect breatheEffect = new PulseEffect();
            breatheEffect.cycles = this.cycles;
            breatheEffect.persistEffect = this.persistEffect;
            breatheEffect.color = this.color;
            breatheEffect.fromColor = this.fromColor;
            breatheEffect.powerOn = this.powerOn;
            breatheEffect.period = this.period;
            return breatheEffect;
        }
    }
}
