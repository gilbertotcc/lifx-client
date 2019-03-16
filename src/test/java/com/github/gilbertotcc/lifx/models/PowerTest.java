package com.github.gilbertotcc.lifx.models;

import org.junit.Test;

public class PowerTest {

  @Test(expected = IllegalArgumentException.class)
  public void thatPowerByLabelFails() {
    Power.byLabel("unknown");
  }
}
