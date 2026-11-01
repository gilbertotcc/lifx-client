package com.github.gilbertotcc.lifx;

import com.github.gilbertotcc.lifx.exception.LifxCallException;

/**
 * Outcome of a client call: a {@link Success} (with or without a result) or a {@link Failure}. No exception is thrown
 * to callers.
 *
 * @param <T> the type of the result carried by {@link Success.Some}
 */
public sealed interface LifxResult<T> {

  /**
   * A call that completed with a 2xx response.
   *
   * @param <T> the type of the result carried by {@link Some}
   */
  sealed interface Success<T> extends LifxResult<T> {
    /**
     * A successful response without a body, such as a {@code fast} request answered with 202.
     *
     * @param <T> the type of the result that would have been carried
     */
    record None<T>() implements Success<T> {}

    /**
     * A successful response with a body.
     *
     * @param <T> the type of the result
     * @param result the response content
     */
    record Some<T>(T result) implements Success<T> {}
  }

  /**
   * A call that failed: an HTTP error status, an I/O or (de)serialization error, or, for queries, a successful
   * response without a body.
   *
   * @param <T> the type of the result that a success would have carried
   * @param lifxCallException the failure, whose cause details the reason
   */
  record Failure<T>(LifxCallException lifxCallException) implements LifxResult<T> {}
}
