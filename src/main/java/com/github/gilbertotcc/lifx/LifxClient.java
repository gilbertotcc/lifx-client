package com.github.gilbertotcc.lifx;

import java.time.Duration;
import java.util.List;

import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;

public interface LifxClient {

  static LifxClient newLifxClientFor(final String accessToken) {
    return LifxClientImpl.createNewClientFor(accessToken);
  }

  List<Light> listLights(final LightSelector lightSelector);

  List<Result> setLightsState(final LightSelector lightSelector, final State state);

  List<OperationResult> setLightsStates(final LightsStatesDto lightsStatesDto);

  List<Result> setLightsStateDelta(final LightSelector lightSelector, final StateDelta stateDelta);

  List<Result> toggleLightsPower(final LightSelector lightSelector, final Duration duration);

  List<Result> doBreatheEffect(final LightSelector lightSelector, final BreatheEffect breatheEffect);

  List<Result> doPulseEffect(final LightSelector lightSelector, final PulseEffect pulseEffect);

  List<Result> transitToNextStateOf(final LightSelector lightSelector, final Cycle cycle);

  Color validateColor(final String colorString);
}
