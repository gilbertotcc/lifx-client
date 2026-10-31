package com.github.gilbertotcc.lifx.operations;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationOutputTest {

  @Test
  void fromShouldMapPresentBodyToSome() {
    var output = OperationOutput.<String, Integer>from(String::length).apply(Optional.of("body"));

    assertEquals(new OperationOutput.Some<>(4), output);
  }

  @Test
  void fromShouldMapMissingBodyToEmpty() {
    var output = OperationOutput.<String, Integer>from(String::length).apply(Optional.empty());

    assertEquals(new OperationOutput.Empty<Integer>(), output);
  }
}
