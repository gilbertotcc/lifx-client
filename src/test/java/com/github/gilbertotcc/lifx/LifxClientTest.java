package com.github.gilbertotcc.lifx;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LifxClientTest {

    @Test
    public void lifxClientOfShouldSuccess() {
        LifxClient lifxClient = LifxClient.lifxClientOf("accessToken");
        assertNotNull(lifxClient);
    }
}
