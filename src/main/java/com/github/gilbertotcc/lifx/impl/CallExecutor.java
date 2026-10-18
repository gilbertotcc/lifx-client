package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@AllArgsConstructor
public class CallExecutor<T> {

  private final Call<T> call;

  /**
   * Synchronously executes the HTTP call.
   *
   * @return the response body on the right, possibly null for a successful response without a body;
   *     otherwise a {@link LifxCallException} on the left, wrapping a call execution failure or a
   *     {@link LifxErrorException} for a non-2xx response
   */
  public Either<LifxCallException, T> execute() {
    return Try.of(call::execute)
      .toEither()
      .mapLeft(throwable -> LifxCallException.with(call, throwable))
      .filterOrElse(
        Response::isSuccessful,
        errorResponse -> LifxCallException.with(call, LifxErrorException.from(errorResponse))
      )
      .map(Response::body)
      .peekLeft(error -> log.error("Call {} failed with error: {}", call.request(), error.getMessage()));
  }
}
