package com.github.gilbertotcc.lifx;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.operations.CommandOutput;
import com.github.gilbertotcc.lifx.operations.DoBreatheEffectInput;
import com.github.gilbertotcc.lifx.operations.DoPulseEffectInput;
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.ListLightsOutput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateDeltaInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStatesInput;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerInput;
import com.github.gilbertotcc.lifx.operations.TransitToNextStateInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorOutput;
import io.vavr.control.Either;

import java.time.Duration;
import java.util.List;

public interface LifxClient {

  static LifxClient newLifxClientFor(final String accessToken) {
    return LifxClientImpl.createNewClientFor(accessToken);
  }

  List<Light> listLights();

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#listLights(ListLightsInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Light> listLights(final LightSelector lightSelector);

  Either<LifxCallException, ListLightsOutput> listLights(ListLightsInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#setLightsState(SetLightsStateInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Result> setLightsState(final LightSelector lightSelector, final State state);

  Either<LifxCallException, CommandOutput<List<Result>>> setLightsState(SetLightsStateInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#setLightsStates(SetLightsStatesInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<OperationResult> setLightsStates(final LightsStatesDto lightsStatesDto);

  Either<LifxCallException, CommandOutput<List<OperationResult>>> setLightsStates(SetLightsStatesInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#setLightsStateDelta(SetLightsStateDeltaInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Result> setLightsStateDelta(final LightSelector lightSelector, final StateDelta stateDelta);

  Either<LifxCallException, CommandOutput<List<Result>>> setLightsStateDelta(SetLightsStateDeltaInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#toggleLightsPower(ToggleLightsPowerInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Result> toggleLightsPower(final LightSelector lightSelector, final Duration duration);

  Either<LifxCallException, CommandOutput<List<Result>>> toggleLightsPower(ToggleLightsPowerInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#doBreatheEffect(DoBreatheEffectInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Result> doBreatheEffect(final LightSelector lightSelector, final BreatheEffect breatheEffect);

  Either<LifxCallException, CommandOutput<List<Result>>> doBreatheEffect(DoBreatheEffectInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#doPulseEffect(DoPulseEffectInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Result> doPulseEffect(final LightSelector lightSelector, final PulseEffect pulseEffect);

  Either<LifxCallException, CommandOutput<List<Result>>> doPulseEffect(DoPulseEffectInput input);

  /**
   * Do not use this method.
   *
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#transitToNextState(TransitToNextStateInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  List<Result> transitToNextStateOf(final LightSelector lightSelector, final Cycle cycle);

  Either<LifxCallException, CommandOutput<List<Result>>> transitToNextState(TransitToNextStateInput input);

  /**
   * Do not use this method.
   * 
   * @deprecated Since version 1.2.0 this method is replaced by the method
   *   {@link LifxClient#validateColor(ValidateColorInput)} with a more functional fashion.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  Color validateColor(final String colorString);

  Either<LifxCallException, ValidateColorOutput> validateColor(ValidateColorInput input);
}
