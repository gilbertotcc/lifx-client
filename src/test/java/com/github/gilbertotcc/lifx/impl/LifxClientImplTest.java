package com.github.gilbertotcc.lifx.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LifxClientImplTest {

    @Test
    public void createNewlifxClientImplShouldSuccess() {
        LifxClientImpl lifxClient = LifxClientImpl.createNew("accessToken");
        assertNotNull(lifxClient);
    }
}
