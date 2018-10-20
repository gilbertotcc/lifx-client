package com.github.gilbertotcc.lifx.models;

public interface LightsSelector {

    LightsSelector ALL = () -> "all";

    String getIdentifier();

    static LightsSelector id(final String lightId) {
        return () -> String.format("id:%s", lightId);
    }
}
