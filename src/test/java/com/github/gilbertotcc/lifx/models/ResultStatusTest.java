package com.github.gilbertotcc.lifx.models;

import org.junit.Test;

public class ResultStatusTest {

  @Test(expected = IllegalArgumentException.class)
  public void thatStatusByLabelFails() {
    Result.Status.byLabel("unknown");
  }
}
