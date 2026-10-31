package com.github.gilbertotcc.lifx.operations;

import java.util.Optional;
import java.util.function.Function;

/**
 * Output of an operation: the response result, or nothing when LIFX accepts the request without a response body
 * (e.g. a {@code fast} request answered with {@code 202 Accepted}).
 */
public sealed interface OperationOutput<T> {

  /**
   * Output of an operation whose successful response has a body.
   */
  record Some<T>(T result) implements OperationOutput<T> {}

  /**
   * Output of an operation whose successful response has no body.
   */
  record Empty<T>() implements OperationOutput<T> {}

  /**
   * Returns a function that maps an optional response body to an output.
   *
   * @param mapper extracts the result from a present body
   * @return a function yielding {@link Some} with the mapped body when present, otherwise {@link Empty}
   */
  static <T, U> Function<Optional<T>, OperationOutput<U>> from(Function<T, U> mapper) {
    return optionalBody -> optionalBody
      .map(mapper)
      .<OperationOutput<U>>map(Some::new)
      .orElseGet(Empty::new);
  }
}
