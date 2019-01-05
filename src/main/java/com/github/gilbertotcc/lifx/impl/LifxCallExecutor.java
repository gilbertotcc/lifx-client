package com.github.gilbertotcc.lifx.impl;

import static java.lang.String.format;

import java.io.IOException;
import java.util.Optional;

import com.github.gilbertotcc.lifx.exception.LifxRemoteException;
import lombok.Value;
import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

@Value(staticConstructor = "of")
class LifxCallExecutor<T> {

    private Call<T> call;

    T getResponse() throws LifxRemoteException {
        try {
            final Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            throw LifxRemoteException.of(response);
        } catch (IOException e) {
            throw new LifxRemoteException(format("Error occurred while calling LIFX HTTP API (%s %s)",
                    Optional.of(call).map(Call::request).map(Request::method).orElse(null),
                    Optional.of(call).map(Call::request).map(Request::url).map(HttpUrl::toString).orElse(null)),
                    e
            );
        }
    }
}
