package com.github.gilbertotcc.lifx.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    public static class ErrorDetail {

        @JsonProperty("field")
        private String field;

        @JsonProperty("message")
        private List<String> messages;

        private ErrorDetail() {}

        public String getField() {
            return field;
        }

        public List<String> getMessages() {
            return messages;
        }
    }

    @JsonProperty("error")
    private String errorMessage;

    @JsonProperty("errors")
    private List<ErrorDetail> details;

    private Error() {}

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<ErrorDetail> getDetails() {
        return details;
    }
}
