package com.github.gilbertotcc.lifx.models;

public interface Selector {

    Selector ALL = () -> "all";

    String getIdentifier();

    static Selector id(final String lightId) {
        return () -> String.format("id:%s", lightId);
    }
}
