package com.github.gilbertotcc.lifx.models;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Results<T> {

    @JsonProperty("results")
    private List<T> results = Collections.emptyList();

    private Results() {}

    public @Nonnull  List<T> getResults() {
        return results;
    }
}
