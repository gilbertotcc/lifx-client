package com.github.gilbertotcc.lifx.impl;

import static com.github.gilbertotcc.lifx.util.JacksonUtils.OBJECT_MAPPER;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

import java.io.IOException;
import java.util.function.Function;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.models.Error;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Value(staticConstructor = "of")
class LifxCallExecutor<T> {

  private Call<T> call;

  @SuppressWarnings("unchecked")
  T getResponse() {
    return Try.of(call::execute)
      .filter(Response::isSuccessful, createLifxErrorException())
      .map(Response::body)
      .mapFailure(Case($(instanceOf(IOException.class)), error -> new LifxCallException(call, error)))
      .onFailure(e -> log.error("Call {} failed with error: {}", call.request(), e.getMessage()))
      .get();
  }

  private Function<Response<?>, LifxErrorException> createLifxErrorException() {
    return response -> Try.of(() -> new Tuple2<>(response.code(), response.errorBody()))
      .mapTry(tuple -> {
        LifxErrorType errorType = LifxErrorType.byHttpCode(tuple._1).orElse(LifxErrorType.UNKNOWN);
        Error error = OBJECT_MAPPER.readerFor(Error.class).readValue(tuple._2.string());
        return new LifxErrorException(errorType, error);
      })
      .getOrElse(LifxErrorException.GENERIC_LIFX_ERROR);
  }
}
