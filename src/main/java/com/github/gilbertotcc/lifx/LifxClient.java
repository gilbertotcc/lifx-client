package com.github.gilbertotcc.lifx;

import java.util.List;

import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.Results;
import com.github.gilbertotcc.lifx.models.State;

public interface LifxClient {

    static LifxClient lifxClientOf(final String accessToken) {
        return LifxClientImpl.createNew(accessToken);
    }

    List<Light> listLights(final LightsSelector lightsSelector);

    List<Results.Result> setLightsState(final LightsSelector lightsSelector, final State state);
}
