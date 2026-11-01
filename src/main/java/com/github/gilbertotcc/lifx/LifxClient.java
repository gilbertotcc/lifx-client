package com.github.gilbertotcc.lifx;

import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.operations.DoBreatheEffectInput;
import com.github.gilbertotcc.lifx.operations.DoPulseEffectInput;
import com.github.gilbertotcc.lifx.operations.ListLightsInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateDeltaInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStateInput;
import com.github.gilbertotcc.lifx.operations.SetLightsStatesInput;
import com.github.gilbertotcc.lifx.operations.ToggleLightsPowerInput;
import com.github.gilbertotcc.lifx.operations.TransitToNextStateInput;
import com.github.gilbertotcc.lifx.operations.ValidateColorInput;

import java.util.List;

public interface LifxClient {

  static LifxClient newLifxClientFor(final String accessToken) {
    return LifxClientImpl.createNewClientFor(accessToken);
  }

  LifxResult<List<Light>> listLights(ListLightsInput input);

  LifxResult<List<Result>> setLightsState(SetLightsStateInput input);

  LifxResult<List<OperationResult>> setLightsStates(SetLightsStatesInput input);

  LifxResult<List<Result>> setLightsStateDelta(SetLightsStateDeltaInput input);

  LifxResult<List<Result>> toggleLightsPower(ToggleLightsPowerInput input);

  LifxResult<List<Result>> doBreatheEffect(DoBreatheEffectInput input);

  LifxResult<List<Result>> doPulseEffect(DoPulseEffectInput input);

  LifxResult<List<Result>> transitToNextState(TransitToNextStateInput input);

  LifxResult<Color> validateColor(ValidateColorInput input);
}
