package com.github.gilbertotcc.lifx;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.Selector;
import org.junit.Test;

public class LifxClientTestIT {

    private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

    @Test
    public void listLightsShouldSuccess() {
        LifxClient lifxClient = LifxClient.lifxClientOf(ACCESS_TOKEN);
        List<Light> lights = lifxClient.listLights(Selector.all());

        assertEquals(1, lights.size());
    }
}
