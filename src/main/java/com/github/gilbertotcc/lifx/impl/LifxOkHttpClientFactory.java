package com.github.gilbertotcc.lifx.impl;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

class LifxOkHttpClientFactory {

    static final LifxOkHttpClientFactory INSTANCE = new LifxOkHttpClientFactory();

    // Shared OkHttpClient prevents OutOfMemoryError under high workloads
    private static final OkHttpClient OK_HTTP_CLIENT_INSTANCE = new OkHttpClient();

    private LifxOkHttpClientFactory() {}

    OkHttpClient getOkHttpClient(final String accessToken, final HttpLoggingInterceptor.Logger logger, final boolean verboseLogging) {
        return OK_HTTP_CLIENT_INSTANCE.newBuilder()
                .addInterceptor(accessTokenInterceptor(accessToken))
                .addInterceptor(loggingInterceptor(logger, verboseLogging))
                .build();
    }

    private static Interceptor accessTokenInterceptor(final String accessToken) {
        return chain -> {
            final Request authRequest = chain.request().newBuilder()
                    .addHeader("Authorization", String.format("Bearer %s", accessToken))
                    .build();
            return chain.proceed(authRequest);
        };
    }

    private static Interceptor loggingInterceptor(final HttpLoggingInterceptor.Logger logger, final boolean verboseLogging) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(logger);
        httpLoggingInterceptor.setLevel(verboseLogging ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }
}
