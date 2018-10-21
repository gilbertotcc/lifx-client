package com.github.gilbertotcc.lifx.impl;

import java.io.IOException;

import com.github.gilbertotcc.lifx.exception.LifxRemoteException;
import retrofit2.Call;
import retrofit2.Response;

class LifxCallExecutor<T> {

    private Call<T> call;

    private LifxCallExecutor(final Call<T> call) {
        this.call = call;
    }

    static <T> LifxCallExecutor<T> of(final Call<T> call) {
        return new LifxCallExecutor<>(call);
    }

    T getResponse() {
        try {
            final Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            throw LifxRemoteException.of(response);
        } catch (IOException e) {
            throw new LifxRemoteException("Error occurred while calling LIFX HTTP API", e);
        }
    }
}
