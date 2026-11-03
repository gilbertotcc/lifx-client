package com.github.gilbertotcc.lifx.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PowerTest {

  @Test
  void thatPowerByLabelIsCaseInsensitive() {
    assertEquals(Power.ON, Power.byLabel("ON"));
    assertEquals(Power.OFF, Power.byLabel("Off"));
  }

  @Test
  void thatPowerByLabelFails() {
    assertThrows(IllegalArgumentException.class, () -> Power.byLabel("unknown"));
  }
}
