package com.github.gilbertotcc.lifx.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PowerTest {

  @Test
  void thatPowerByLabelFails() {
    assertThrows(IllegalArgumentException.class, () -> Power.byLabel("unknown"));
  }
}
