package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class CallExecutor<T> {

  private final Call<T> call;

  /**
   * Synchronously executes the HTTP call.
   *
   * @return the response body on the right, as an {@link Option} that is empty for a successful response without
   *     a body; otherwise a {@link LifxCallException} on the left, wrapping a call execution failure or a
   *     {@link LifxErrorException} for a non-2xx response
   */
  public Either<LifxCallException, Option<T>> execute() {
    return Try.of(call::execute)
      .toEither()
      .mapLeft(throwable -> LifxCallException.with(call, throwable))
      .filterOrElse(
        Response::isSuccessful,
        errorResponse -> LifxCallException.with(call, LifxErrorException.from(errorResponse))
      )
      .map(response -> Option.of(response.body()))
      .peekLeft(error -> log.error("Call {} failed with error: {}", call.request(), error.getMessage()));
  }

  /**
   * Synchronously executes the HTTP call, for calls whose successful response must have a body.
   *
   * @return the response body on the right; otherwise a {@link LifxCallException} on the left, as for
   *     {@link #execute()}, or wrapping an {@link IOException} if a successful response has no body
   */
  public Either<LifxCallException, T> executeExpectingBody() {
    return execute()
      .flatMap(body -> body.toEither(
        () -> LifxCallException.with(call, new IOException("Successful response has no body"))
      ));
  }
}
