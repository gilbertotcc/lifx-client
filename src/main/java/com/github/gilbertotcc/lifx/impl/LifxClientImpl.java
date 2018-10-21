package com.github.gilbertotcc.lifx.impl;

import static java.util.function.Predicate.isEqual;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStates;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.TogglePower;
import com.github.gilbertotcc.lifx.models.converter.LightsSelectorConverter;
import com.github.gilbertotcc.lifx.models.converter.StringConverterFactory;
import com.github.gilbertotcc.lifx.util.JacksonUtils;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LifxClientImpl implements LifxClient {

    private static final String LIFX_BASE_URL = "https://api.lifx.com";

    private static final Logger LOG = Logger.getLogger(LifxClientImpl.class.getName());

    private final LifxApi lifxApi;

    // Mainly for testing purposes
    private LifxClientImpl(final LifxApi lifxApi) {
        this.lifxApi = lifxApi;
    }

    // Mainly for testing purposes
    static LifxClientImpl createNewClientFor(final String baseUrl, final String accessToken) {
        Objects.requireNonNull(baseUrl, "baseUrl == null");
        Objects.requireNonNull(accessToken, "accessToken == null");

        final OkHttpClient okHttpClient = LifxOkHttpClientFactory.INSTANCE
                .getOkHttpClient(accessToken, LOG::info, isLoggingVerbose());
        final LifxApi lifxApi = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(StringConverterFactory.of(LightsSelector.class, new LightsSelectorConverter()))
                .addConverterFactory(JacksonConverterFactory.create(JacksonUtils.OBJECT_MAPPER))
                .client(okHttpClient)
                .build()
                .create(LifxApi.class);
        return new LifxClientImpl(lifxApi);
    }

    public static LifxClientImpl createNewClientFor(final String accessToken) {
        return createNewClientFor(LIFX_BASE_URL, accessToken);
    }

    @Override
    public List<Light> listLights(final LightsSelector lightsSelector) {
        LOG.info(() -> String.format("List lights (selector: %s)", lightsSelector.getIdentifier()));
        return LifxCallExecutor.of(lifxApi.listLights(lightsSelector)).getResponse();
    }

    @Override
    public List<Result> setLightsState(final LightsSelector lightsSelector, final State state) {
        LOG.info(() -> String.format("Set lights state of %s to %s", lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(state, ToStringStyle.JSON_STYLE)));
        return LifxCallExecutor.of(lifxApi.setLightsState(lightsSelector, state)).getResponse()
                .getResults();
    }

    @Override
    public List<OperationResult> setLightsStates(final LightsStates lightsStates) {
        LOG.info(() -> String.format("Set lights states of %s", lightsSelectorListOf(lightsStates)));
        return LifxCallExecutor.of(lifxApi.setLightsStates(lightsStates)).getResponse()
                .getResults();
    }

    @Override
    public List<Result> setLightsStateDelta(final LightsSelector lightsSelector, final StateDelta stateDelta) {
        LOG.info(() -> String.format("Set lights state delta of %s to %s", lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(stateDelta, ToStringStyle.JSON_STYLE)));
        return LifxCallExecutor.of(lifxApi.setLightsStateDelta(lightsSelector, stateDelta)).getResponse()
                .getResults();
    }

    @Override
    public List<Result> toggleLightsPower(final LightsSelector lightsSelector, final Duration duration) {
        LOG.info(() -> String.format("Toggle lights power of %s in %s seconds", lightsSelector.getIdentifier(), duration.getSeconds()));
        return LifxCallExecutor.of(lifxApi.togglePower(lightsSelector, TogglePower.in(duration))).getResponse()
                .getResults();
    }

    @Override
    public List<Result> doBreatheEffect(final LightsSelector lightsSelector, final BreatheEffect breatheEffect) {
        LOG.info(() -> String.format("Do breathe effect with %s. Settings: %s", lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(breatheEffect, ToStringStyle.JSON_STYLE)));
        return LifxCallExecutor.of(lifxApi.breatheEffect(lightsSelector, breatheEffect)).getResponse()
                .getResults();
    }

    private static boolean isLoggingVerbose() {
        return Stream.of(Level.FINE, Level.FINER, Level.FINEST, Level.ALL).anyMatch(isEqual(LOG.getLevel()));
    }

    private static String lightsSelectorListOf(final LightsStates lightsStates) {
        return lightsStates.getLightsStates().stream()
                .map(LightsState::getLightsSelector)
                .map(LightsSelector::getIdentifier)
                .collect(Collectors.joining(","));
    }
}
