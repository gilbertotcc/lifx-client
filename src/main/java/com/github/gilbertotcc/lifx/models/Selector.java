package com.github.gilbertotcc.lifx.models;

public interface Selector {

    String getIdentifier();

    static Selector all() {
        return () -> "all";
    }

    static Selector id(final String lightId) {
        return () -> String.format("id:%s", lightId);
    }
}
