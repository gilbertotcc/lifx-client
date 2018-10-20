package com.github.gilbertotcc.lifx.models;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationResult {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Operation {

        @JsonProperty("selector")
        private String selector;

        private Operation() {}

        public String getSelector() {
            return selector;
        }
    }

    @JsonProperty("operation")
    private Operation operation;

    @JsonProperty("results")
    private List<Result> results = Collections.emptyList();

    @JsonProperty("error")
    private String error;

    private OperationResult() {}

    public Operation getOperation() {
        return operation;
    }

    public @Nonnull List<Result> getResults() {
        return results;
    }

    public String getError() {
        return error;
    }
}
