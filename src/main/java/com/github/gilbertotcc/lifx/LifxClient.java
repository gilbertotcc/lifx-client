package com.github.gilbertotcc.lifx;

import java.time.Duration;
import java.util.List;

import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
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

    List<Light> listLights(final LightsSelector lightsSelector);

    List<Result> setLightsState(final LightsSelector lightsSelector, final State state);

    List<OperationResult> setLightsStates(final LightsStates lightsStates);

    List<Result> setLightsStateDelta(final LightsSelector lightsSelector, final StateDelta stateDelta);

    List<Result> toggleLightsPower(final LightsSelector lightsSelector, final Duration duration);

    List<Result> doBreatheEffect(final LightsSelector lightsSelector, final BreatheEffect breatheEffect);
}
