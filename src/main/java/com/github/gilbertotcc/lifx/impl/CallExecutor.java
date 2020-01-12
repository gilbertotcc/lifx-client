package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@AllArgsConstructor
public class CallExecutor<T> {

  private final Call<T> call;

  public Either<LifxCallException, T> execute() {
    return Try.of(call::execute)
      .toEither()
      .filterOrElse(Response::isSuccessful, LifxErrorException::from)
      .map(Response::body)
      .mapLeft(Case($(instanceOf(IOException.class)), error -> new LifxCallException(call, error)))
      .peekLeft(error -> log.error("Call {} failed with error: {}", call.request(), error.getMessage()));
  }
}
