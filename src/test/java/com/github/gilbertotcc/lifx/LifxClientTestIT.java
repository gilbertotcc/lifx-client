package com.github.gilbertotcc.lifx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import com.github.gilbertotcc.lifx.exception.LifxRemoteException;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightsSelector;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Results;
import com.github.gilbertotcc.lifx.models.State;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LifxClientTestIT {

    private static final String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

    private static String lightId = null;
    private static Power lightPower = null;

    @Test
    public void test00_listLightsShouldSuccess() {
        LifxClient lifxClient = LifxClient.lifxClientOf(ACCESS_TOKEN);
        List<Light> lights = lifxClient.listLights(LightsSelector.ALL);

        assertFalse(lights.isEmpty());
        // TODO Add more asserts


        lights.stream().filter(Light::isConnected).findFirst().ifPresent(light -> {
            lightId = light.getId();
            lightPower = "on".equals(light.getPower()) ? Power.OFF : Power.ON; // Very ugly!
        });
    }

    @Test
    public void test01_setLightsStateShouldSuccess() {
        LifxClient lifxClient = LifxClient.lifxClientOf(ACCESS_TOKEN);

        State state = State.builder()
                .power(lightPower)
                .build();

        List<Results.Result> results = lifxClient.setLightsState(LightsSelector.id(lightId), state);

        assertEquals(1, results.size());
        assertEquals(Results.Result.Status.OK, results.get(0).getStatus());
    }

    @Test(expected = LifxRemoteException.class)
    public void listLightsShouldFail() {
        LifxClient lifxClient = LifxClient.lifxClientOf("what access token?");
        lifxClient.listLights(LightsSelector.ALL);
    }
}
