package com.github.gilbertotcc.lifx;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LifxClientTest {

  @Test
  public void newLifxClientForShouldSuccess() {
    LifxClient lifxClient = LifxClient.newLifxClientFor("accessToken");
    assertNotNull(lifxClient);
  }
}
