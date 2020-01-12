package com.github.gilbertotcc.lifx.impl;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

import java.io.IOException;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import io.vavr.control.Try;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Value(staticConstructor = "of")
public class LifxCallExecutor<T> {

  private Call<T> call;

  @SuppressWarnings("unchecked")
  public T getResponse() {
    return Try.of(call::execute)
      .filter(Response::isSuccessful, LifxErrorException::from)
      .map(Response::body)
      .mapFailure(Case($(instanceOf(IOException.class)), error -> new LifxCallException(call, error)))
      .onFailure(e -> log.error("Call {} failed with error: {}", call.request(), e.getMessage()))
      .get();
  }
}
