package com.github.gilbertotcc.lifx.impl;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class LifxOkHttpClientFactory {

    // Shared OkHttpClient prevents OutOfMemoryError under high workloads
    private static final OkHttpClient OK_HTTP_CLIENT_INSTANCE = new OkHttpClient();

    static final LifxOkHttpClientFactory INSTANCE = new LifxOkHttpClientFactory();

    private LifxOkHttpClientFactory() {}

    public OkHttpClient getOkHttpClient(final String bearerAccessToken) {
        return OK_HTTP_CLIENT_INSTANCE.newBuilder()
                .addInterceptor(bearerAccessTokenInterceptorOf(bearerAccessToken))
                .addInterceptor(httpLoggingInterceptor())
                .build();
    }

    private static Interceptor bearerAccessTokenInterceptorOf(@Nonnull final String bearedAccessToken) {
        return chain -> {
            final Request authRequest = chain.request().newBuilder()
                    .addHeader("Authorization", String.format("Bearer %s", bearedAccessToken))
                    .build();
            return chain.proceed(authRequest);
        };
    }

    private static Interceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }
}
