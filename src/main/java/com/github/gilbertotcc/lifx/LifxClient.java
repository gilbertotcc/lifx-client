package com.github.gilbertotcc.lifx;

import java.time.Duration;
import java.util.List;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.Color;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.StateDelta;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.LightsStates;
import com.github.gilbertotcc.lifx.models.OperationResult;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.State;

public interface LifxClient {

  static LifxClient newLifxClientFor(final String accessToken) {
    return LifxClientImpl.createNewClientFor(accessToken);
  }

  List<Light> listLights(final LightsSelector lightsSelector) throws LifxCallException;

  List<Result> setLightsState(final LightsSelector lightsSelector, final State state) throws LifxCallException;

  List<OperationResult> setLightsStates(final LightsStates lightsStates) throws LifxCallException;

  List<Result> setLightsStateDelta(final LightsSelector lightsSelector, final StateDelta stateDelta)
    throws LifxCallException;

  List<Result> toggleLightsPower(final LightsSelector lightsSelector, final Duration duration)
    throws LifxCallException;

  List<Result> doBreatheEffect(final LightsSelector lightsSelector, final BreatheEffect breatheEffect)
    throws LifxCallException;

  List<Result> doPulseEffect(final LightsSelector lightsSelector, final PulseEffect pulseEffect)
    throws LifxCallException;

  List<Result> transitToNextStateOf(final LightsSelector lightsSelector, final Cycle cycle)
    throws LifxCallException;

  Color validateColor(final String colorString) throws LifxCallException;
}
