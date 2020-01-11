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
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.ListLightsOutput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateDeltaInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStatesInput;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerInput;
import io.vavr.control.Either;

import java.time.Duration;
import java.util.List;

public interface LifxClient {

  static LifxClient newLifxClientFor(final String accessToken) {
    return LifxClientImpl.createNewClientFor(accessToken);
  }

  List<Light> listLights();

  @Deprecated(since = "1.2.0")
  List<Light> listLights(final LightSelector lightSelector);

  Either<LifxCallException, ListLightsOutput> listLights(ListLightsInput input);

  @Deprecated(since = "1.2.0")
  List<Result> setLightsState(final LightSelector lightSelector, final State state);

  Either<LifxCallException, CommandOutput<List<Result>>> setLightsState(SetLightsStateInput input);

  @Deprecated(since = "1.2.0")
  List<OperationResult> setLightsStates(final LightsStatesDto lightsStatesDto);

  Either<LifxCallException, CommandOutput<List<OperationResult>>> setLightsStates(SetLightsStatesInput input);

  @Deprecated(since = "1.2.0")
  List<Result> setLightsStateDelta(final LightSelector lightSelector, final StateDelta stateDelta);

  Either<LifxCallException, CommandOutput<List<Result>>> setLightsStateDelta(SetLightsStateDeltaInput input);

  @Deprecated(since = "1.2.0")
  List<Result> toggleLightsPower(final LightSelector lightSelector, final Duration duration);

  Either<LifxCallException, CommandOutput<List<Result>>> toggleLightsPower(ToggleLightsPowerInput input);

  @Deprecated(since = "1.2.0")
  List<Result> doBreatheEffect(final LightSelector lightSelector, final BreatheEffect breatheEffect);

  Either<LifxCallException, CommandOutput<List<Result>>> doBreatheEffect(DoBreatheEffectInput input);

  List<Result> doPulseEffect(final LightSelector lightSelector, final PulseEffect pulseEffect);

  List<Result> transitToNextStateOf(final LightSelector lightSelector, final Cycle cycle);

  Color validateColor(final String colorString);
}
