package com.github.gilbertotcc.lifx;

import java.util.List;

import com.github.gilbertotcc.lifx.impl.LifxClientImpl;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.Selector;

public interface LifxClient {

    static LifxClient lifxClientOf(final String accessToken) {
        return LifxClientImpl.createNew(accessToken);
    }

    List<Light> listLights(final Selector selector);
}
