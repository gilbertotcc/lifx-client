package com.github.gilbertotcc.lifx;

import java.util.List;

import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
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
}
