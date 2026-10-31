package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.LifxClient;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.converter.LightSelectorConverter;
import com.github.gilbertotcc.lifx.models.converter.StringConverterFactory;
import com.github.gilbertotcc.lifx.operations.CommandOutput;
import com.github.gilbertotcc.lifx.operations.DoBreatheEffectCommand;
import com.github.gilbertotcc.lifx.operations.DoBreatheEffectInput;
import com.github.gilbertotcc.lifx.operations.DoPulseEffectCommand;
import com.github.gilbertotcc.lifx.operations.DoPulseEffectInput;
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.ListLightsQuery;
import com.github.gilbertotcc.lifx.operations.SetLightsStateCommand;
import com.github.gilbertotcc.lifx.operations.SetLightsStateDeltaCommand;
import com.github.gilbertotcc.lifx.operations.SetLightsStateDeltaInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStatesCommand;
import com.github.gilbertotcc.lifx.operations.SetLightsStatesInput;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerCommand;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerInput;
import com.github.gilbertotcc.lifx.operations.TransitToNextStateCommand;
import com.github.gilbertotcc.lifx.operations.TransitToNextStateInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorQuery;
import com.github.gilbertotcc.lifx.util.JacksonUtils;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jspecify.annotations.Nullable;
import retrofit2.Retrofit;

import java.util.List;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LifxClientImpl implements LifxClient {

  private static final String LIFX_BASE_URL = "https://api.lifx.com";

  // Operations
  private final ListLightsQuery listLightsQuery;
  private final SetLightsStateCommand setLightsStateCommand;
  private final SetLightsStatesCommand setLightsStatesCommand;
  private final SetLightsStateDeltaCommand setLightsStateDeltaCommand;
  private final ToggleLightsPowerCommand toggleLightsPowerCommand;
  private final DoBreatheEffectCommand doBreatheEffectCommand;
  private final DoPulseEffectCommand doPulseEffectCommand;
  private final TransitToNextStateCommand transitToNextStateCommand;
  private final ValidateColorQuery validateColorQuery;

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
      new ListLightsQuery(lifxApi),
      new SetLightsStateCommand(lifxApi),
      new SetLightsStatesCommand(lifxApi),
      new SetLightsStateDeltaCommand(lifxApi),
      new ToggleLightsPowerCommand(lifxApi),
      new DoBreatheEffectCommand(lifxApi),
      new DoPulseEffectCommand(lifxApi),
      new TransitToNextStateCommand(lifxApi),
      new ValidateColorQuery(lifxApi)
    );
  }

  public static LifxClientImpl createNewClientFor(final String accessToken) {
    return createNewClientFor(LIFX_BASE_URL, accessToken);
  }

  /**
   * Checks the URL and token for null, without validating their contents.
   *
   * @return a valid null value, or an invalid result containing an {@link IllegalArgumentException} whose
   *     message lists all null parameters in URL-then-token order
   */
  // The valid value is null: only the validation errors matter
  static Validation<IllegalArgumentException, @Nullable Object> validate(@Nullable String baseUrl,
                                                                         @Nullable String accessToken) {
    return Validation.combine(
      notNull("baseUrl", baseUrl),
      notNull("accessToken", accessToken))
      .ap((validBaseUrl, validAccessToken) -> null)
      .mapError(errors -> new IllegalArgumentException(errors.collect(joining(", "))));
  }

  /**
   * Returns a valid value when non-null, or an invalid result with the message "parameterName is null".
   *
   * @param parameterName the name to substitute into the error message
   */
  static <T> Validation<String, T> notNull(String parameterName, @Nullable T value) {
    return value == null ? invalid("%s is null".formatted(parameterName)) : valid(value);
  }

  @Override
  public Either<LifxCallException, CommandOutput.SomeOutput<List<Light>>> listLights(ListLightsInput input) {
    return listLightsQuery.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> setLightsState(SetLightsStateInput input) {
    return setLightsStateCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<OperationResult>>> setLightsStates(SetLightsStatesInput input) {
    return setLightsStatesCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> setLightsStateDelta(SetLightsStateDeltaInput input) {
    return setLightsStateDeltaCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> toggleLightsPower(ToggleLightsPowerInput input) {
    return toggleLightsPowerCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> doBreatheEffect(DoBreatheEffectInput input) {
    return doBreatheEffectCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> doPulseEffect(DoPulseEffectInput input) {
    return doPulseEffectCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> transitToNextState(TransitToNextStateInput input) {
    return transitToNextStateCommand.execute(input);
  }

  @Override
  public Either<LifxCallException, CommandOutput.SomeOutput<Color>> validateColor(ValidateColorInput input) {
    return validateColorQuery.execute(input);
  }
}
