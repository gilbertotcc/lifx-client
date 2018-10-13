package com.github.gilbertotcc.lifx.impl;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class LifxOkHttpClientFactory {

    // Shared OkHttpClient prevents OutOfMemoryError under high workloads
    private static final OkHttpClient OK_HTTP_CLIENT_INSTANCE = new OkHttpClient();

    public static final LifxOkHttpClientFactory INSTANCE = new LifxOkHttpClientFactory();

    private LifxOkHttpClientFactory() {}

    public OkHttpClient getOkHttpClient(final String bearerAccessToken) {
        return OK_HTTP_CLIENT_INSTANCE.newBuilder()
                .addInterceptor(bearerAccessTokenInterceptorOf(bearerAccessToken))
                .build();
    }

    private Interceptor bearerAccessTokenInterceptorOf(@Nonnull final String bearedAccessToken) {
        return chain -> {
            final Request authRequest = chain.request().newBuilder()
                    .addHeader("Authorization", String.format("Bearer %s", bearedAccessToken))
                    .build();
            return chain.proceed(authRequest);
        };
    }
}
