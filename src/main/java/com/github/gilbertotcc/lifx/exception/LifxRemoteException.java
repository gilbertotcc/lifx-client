package com.github.gilbertotcc.lifx.exception;

import java.io.IOException;
import java.util.Optional;

import com.github.gilbertotcc.lifx.models.Error;
import com.github.gilbertotcc.lifx.util.JacksonUtils;
import retrofit2.Response;

public class LifxRemoteException extends RuntimeException {

    public LifxRemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifxRemoteException(String message) {
        super(message);
    }

    public static LifxRemoteException of(final Response<?> response) {
        Optional<Error> errorOpt = findErrorWithin(response);
        return errorOpt.map(error -> new LifxRemoteException(error.getErrorMessage()))
                .orElse(new LifxRemoteException(String.format("LIFX call failed with %d response code", response.code())));
    }

    private static Optional<Error> findErrorWithin(final Response<?> response) {
        return Optional.ofNullable(response.errorBody()).map(errorBody -> {
            try {
                return  JacksonUtils.OBJECT_MAPPER.readerFor(Error.class).readValue(errorBody.string());
            } catch (IOException e) {
               return null;
            }
        });
    }
}
