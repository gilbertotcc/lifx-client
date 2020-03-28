package com.github.gilbertotcc.lifx;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
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

import java.util.List;

public interface LifxClient {

  static LifxClient newLifxClientFor(final String accessToken) {
    return LifxClientImpl.createNewClientFor(accessToken);
  }

  Either<LifxCallException, ListLightsOutput> listLights(ListLightsInput input);

  Either<LifxCallException, CommandOutput<List<Result>>> setLightsState(SetLightsStateInput input);

  Either<LifxCallException, CommandOutput<List<OperationResult>>> setLightsStates(SetLightsStatesInput input);

  Either<LifxCallException, CommandOutput<List<Result>>> setLightsStateDelta(SetLightsStateDeltaInput input);

  Either<LifxCallException, CommandOutput<List<Result>>> toggleLightsPower(ToggleLightsPowerInput input);

  Either<LifxCallException, CommandOutput<List<Result>>> doBreatheEffect(DoBreatheEffectInput input);

  Either<LifxCallException, CommandOutput<List<Result>>> doPulseEffect(DoPulseEffectInput input);

  Either<LifxCallException, CommandOutput<List<Result>>> transitToNextState(TransitToNextStateInput input);

  Either<LifxCallException, ValidateColorOutput> validateColor(ValidateColorInput input);
}
