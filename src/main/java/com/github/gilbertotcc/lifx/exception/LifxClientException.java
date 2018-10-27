package com.github.gilbertotcc.lifx.exception;

public class LifxClientException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public LifxClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifxClientException(String message) {
        super(message);
    }
}
