package com.github.gilbertotcc.lifx.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LightsStates {

    @JsonProperty("states")
    private List<LightsState> lightsStates;

    @JsonProperty("defaults")
    private State defaultState;

    @JsonProperty("fast")
    private boolean fast;

    private LightsStates(final List<LightsState> lightsStates, final State defaultState, final boolean fast) {
        this.lightsStates = lightsStates;
        this.defaultState = defaultState;
        this.fast = fast;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<LightsState> getLightsStates() {
        return lightsStates;
    }

    public State getDefaultState() {
        return defaultState;
    }

    public boolean isFast() {
        return fast;
    }

    public static final class Builder {

        private List<LightsState> lightsStates = new ArrayList<>();
        private State defaultState;
        private boolean fast;

        private Builder() {}

        public Builder addLightsState(LightsState lightsState) {
            this.lightsStates.add(lightsState);
            return this;
        }

        public Builder defaultState(State defaultState) {
            this.defaultState = defaultState;
            return this;
        }

        public Builder fast(boolean fast) {
            this.fast = fast;
            return this;
        }

        public LightsStates build() {
            return new LightsStates(lightsStates, defaultState, fast);
        }
    }
}
