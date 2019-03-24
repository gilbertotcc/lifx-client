package com.github.gilbertotcc.lifx.models;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ResultStatusTest {

  @Test
  void thatStatusByLabelFails() {
    assertThrows(IllegalArgumentException.class, () -> Result.Status.byLabel("unknown"));
  }
}
