package com.github.gilbertotcc.lifx.impl;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStates;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.TogglePower;
import com.github.gilbertotcc.lifx.models.converter.LightsSelectorConverter;
import com.github.gilbertotcc.lifx.models.converter.StringConverterFactory;
import com.github.gilbertotcc.lifx.util.JacksonUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LifxClientImpl implements LifxClient {

  private static final String LIFX_BASE_URL = "https://api.lifx.com";

  private final LifxApi lifxApi;

  // Mainly for testing purposes
  static LifxClientImpl createNewClientFor(final String baseUrl, final String accessToken) {
    Objects.requireNonNull(baseUrl, "baseUrl == null");
    Objects.requireNonNull(accessToken, "accessToken == null");

    final OkHttpClient okHttpClient = LifxOkHttpClientFactory.INSTANCE
      .getOkHttpClient(accessToken, log::debug);
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
    log.info("List lights (selector: {})", lightsSelector.getIdentifier());
    return LifxCallExecutor.of(lifxApi.listLights(lightsSelector)).getResponse();
  }

  @Override
  public List<Result> setLightsState(final LightsSelector lightsSelector, final State state) {
    log.info("Set lights state of {} to {}",
      lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(state, ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.setLightsState(lightsSelector, state)).getResponse()
      .getResults();
  }

  @Override
  public List<OperationResult> setLightsStates(final LightsStates lightsStates) {
    log.info("Set lights states of {}", lightsSelectorListOf(lightsStates));
    return LifxCallExecutor.of(lifxApi.setLightsStates(lightsStates)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> setLightsStateDelta(final LightsSelector lightsSelector, final StateDelta stateDelta) {
    log.info("Set lights state delta of {} to {}",
      lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(stateDelta, ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.setLightsStateDelta(lightsSelector, stateDelta)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> toggleLightsPower(final LightsSelector lightsSelector, final Duration duration) {
    log.info("Toggle lights power of {} in {} seconds", lightsSelector.getIdentifier(), duration.getSeconds());
    return LifxCallExecutor.of(lifxApi.togglePower(lightsSelector, TogglePower.in(duration))).getResponse()
      .getResults();
  }

  @Override
  public List<Result> doBreatheEffect(final LightsSelector lightsSelector, final BreatheEffect breatheEffect) {
    log.info("Do breathe effect with {}. Settings: {}",
      lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(breatheEffect, ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.breatheEffect(lightsSelector, breatheEffect)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> doPulseEffect(final LightsSelector lightsSelector, final PulseEffect pulseEffect) {
    log.info("Do pulse effect with {}. Settings: {}",
      lightsSelector.getIdentifier(), ReflectionToStringBuilder.toString(pulseEffect,
      ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.pulseEffect(lightsSelector, pulseEffect)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> transitToNextStateOf(final LightsSelector lightsSelector, final Cycle cycle) {
    log.info("Transit to next state of {}", lightsSelector.getIdentifier());
    return LifxCallExecutor.of(lifxApi.cycle(lightsSelector, cycle)).getResponse()
      .getResults();
  }

  @Override
  public Color validateColor(final String colorString) {
    log.info("Validate color {}", colorString);
    return LifxCallExecutor.of(lifxApi.validateColor(colorString)).getResponse();
  }

  private static String lightsSelectorListOf(final LightsStates lightsStates) {
    return lightsStates.getLightsStates().stream()
      .map(LightsState::getLightsSelector)
      .map(LightsSelector::getIdentifier)
      .collect(Collectors.joining(","));
  }
}
