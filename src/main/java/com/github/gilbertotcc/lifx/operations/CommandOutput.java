package com.github.gilbertotcc.lifx.operations;

/**
 * Output of a command: the response results, or nothing when LIFX accepts the request without a response body
 * (e.g. a {@code fast} request answered with {@code 202 Accepted}).
 */
public sealed interface CommandOutput<T> {

  /**
   * Output of a command whose successful response has a body.
   */
  record SomeOutput<T>(T result) implements CommandOutput<T> {}

  /**
   * Output of a command whose successful response has no body.
   */
  record EmptyOutput<T>() implements CommandOutput<T> {}
}
