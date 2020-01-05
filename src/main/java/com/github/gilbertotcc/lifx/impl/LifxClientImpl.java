package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.operations.CommandOutput;
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.ListLightsOutput;
import com.github.gilbertotcc.lifx.operations.ListLightsQuery;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.TogglePower;
import com.github.gilbertotcc.lifx.models.converter.LightSelectorConverter;
import com.github.gilbertotcc.lifx.models.converter.StringConverterFactory;
import com.github.gilbertotcc.lifx.operations.SetLightsStateCommand;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.util.JacksonUtils;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LifxClientImpl implements LifxClient {

  private static final String LIFX_BASE_URL = "https://api.lifx.com";

  private final LifxApi lifxApi;

  // Operations
  private final ListLightsQuery listLightsQuery;
  private final SetLightsStateCommand setLightsStateCommand;

  // Mainly for testing purposes
  public static LifxClientImpl createNewClientFor(final String baseUrl, final String accessToken) {
    validate(baseUrl, accessToken).get();

    final OkHttpClient okHttpClient = LifxOkHttpClientFactory.INSTANCE.getOkHttpClient(accessToken, log::debug);
    final LifxApi lifxApi = new Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(StringConverterFactory.of(LightSelector.class, new LightSelectorConverter()))
      .addConverterFactory(JacksonConverterFactory.create(JacksonUtils.OBJECT_MAPPER))
      .client(okHttpClient)
      .build()
      .create(LifxApi.class);
    return new LifxClientImpl(
      lifxApi,
      new ListLightsQuery(lifxApi),
      new SetLightsStateCommand(lifxApi)
    );
  }

  public static LifxClientImpl createNewClientFor(final String accessToken) {
    return createNewClientFor(LIFX_BASE_URL, accessToken);
  }

  private static String lightSelectorListOf(final LightsStatesDto lightsStatesDto) {
    return lightsStatesDto.getLightsStates().stream()
      .map(LightsState::getLightSelector)
      .map(LightSelector::identifier)
      .collect(joining(","));
  }

  static Validation<IllegalArgumentException, Object> validate(String baseUrl, String accessToken) {
    return Validation.combine(
      notNull("baseUrl", baseUrl),
      notNull("accessToken", accessToken))
      .ap((validBaseUrl, validAccessToken) -> null)
      .mapError(errors -> new IllegalArgumentException(errors.collect(joining(", "))));
  }

  static <T> Validation<String, T> notNull(String parameterName, T value) {
    return Objects.isNull(value) ? invalid(format("%s is null", parameterName)) : valid(value);
  }

  @Override
  public List<Light> listLights() {
    return getResultOf(listLightsQuery.execute(ListLightsInput.ALL_LIGHTS)).getLights();
  }

  @Override
  public List<Light> listLights(final LightSelector lightsSelector) {
    ListLightsInput listLightsInput = new ListLightsInput(lightsSelector);
    return getResultOf(listLightsQuery.execute(listLightsInput)).getLights();
  }

  @Override
  public Either<LifxCallException, ListLightsOutput> listLights(ListLightsInput input) {
    return listLightsQuery.execute(input);
  }

  @Override
  public List<Result> setLightsState(final LightSelector lightSelector, final State state) {
    var input = new SetLightsStateInput(lightSelector, state);
    return getResultOf(setLightsStateCommand.execute(input)).getResult();
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> setLightsState(SetLightsStateInput input) {
    return setLightsStateCommand.execute(input);
  }

  @Override
  public List<OperationResult> setLightsStates(final LightsStatesDto lightsStatesDto) {
    log.info("Set lights states of {}", lightSelectorListOf(lightsStatesDto));
    return LifxCallExecutor.of(lifxApi.setLightsStates(lightsStatesDto)).getResponse().getResults();
  }

  @Override
  public List<Result> setLightsStateDelta(final LightSelector lightSelector, final StateDelta stateDelta) {
    log.info("Set lights state delta of {} to {}",
      lightSelector.identifier(), ReflectionToStringBuilder.toString(stateDelta, ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.setLightsStateDelta(lightSelector, stateDelta)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> toggleLightsPower(final LightSelector lightSelector, final Duration duration) {
    log.info("Toggle lights power of {} in {} seconds", lightSelector.identifier(), duration.getSeconds());
    return LifxCallExecutor.of(lifxApi.togglePower(lightSelector, TogglePower.in(duration))).getResponse()
      .getResults();
  }

  @Override
  public List<Result> doBreatheEffect(final LightSelector lightSelector, final BreatheEffect breatheEffect) {
    log.info("Do breathe effect with {}. Settings: {}",
      lightSelector.identifier(), ReflectionToStringBuilder.toString(breatheEffect, ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.breatheEffect(lightSelector, breatheEffect)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> doPulseEffect(final LightSelector lightSelector, final PulseEffect pulseEffect) {
    log.info("Do pulse effect with {}. Settings: {}",
      lightSelector.identifier(), ReflectionToStringBuilder.toString(pulseEffect,
        ToStringStyle.JSON_STYLE));
    return LifxCallExecutor.of(lifxApi.pulseEffect(lightSelector, pulseEffect)).getResponse()
      .getResults();
  }

  @Override
  public List<Result> transitToNextStateOf(final LightSelector lightSelector, final Cycle cycle) {
    log.info("Transit to next state of {}", lightSelector.identifier());
    return LifxCallExecutor.of(lifxApi.cycle(lightSelector, cycle)).getResponse()
      .getResults();
  }

  @Override
  public Color validateColor(final String colorString) {
    log.info("Validate color {}", colorString);
    return LifxCallExecutor.of(lifxApi.validateColor(colorString)).getResponse();
  }

  private static <T> T getResultOf(Either<LifxCallException, T> operationResult) {
    return operationResult
      .getOrElseThrow(error -> error);
  }
}
