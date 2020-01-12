package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import retrofit2.Call;

import java.util.function.Function;

@AllArgsConstructor
public class CallExecutor<T> {

  private final Call<T> call;

  public <O> Either<LifxCallException, O> execute(Function<T, O> typeMapper) {
    return Try.of(() -> LifxCallExecutor.of(call).getResponse())
      .toEither()
      .map(typeMapper)
      .mapLeft(this::toLifxCallException);
  }

  private LifxCallException toLifxCallException(Throwable error) {
    return error instanceof LifxCallException
      ? (LifxCallException) error
      : new LifxCallException(error);
  }
}
