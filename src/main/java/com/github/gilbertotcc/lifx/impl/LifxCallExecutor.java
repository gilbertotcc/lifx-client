package com.github.gilbertotcc.lifx.impl;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.models.Error;
import com.github.gilbertotcc.lifx.util.JacksonUtils;
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
    return Try.of(() -> call.execute())
      .filter(isSuccessful(), createLifxErrorException())
      .map(successfulResponse -> successfulResponse.body())
      .mapFailure(Case($(instanceOf(IOException.class)), error -> new LifxCallException(call, error)))
      .onFailure(e -> log.error("Call {} failed with error: {}", call.request(), e.getMessage()))
      .get();
  }

  private Predicate<Response<?>> isSuccessful() {
    return Response::isSuccessful;
  }

  private Function<Response<?>, LifxErrorException> createLifxErrorException() {
    return response -> Try.of(() -> new Tuple2<>(response.code(), response.errorBody()))
      .mapTry(tuple -> new LifxErrorException(
        LifxErrorType.byHttpCode(response.code()).orElse(LifxErrorType.UNKNOWN),
        JacksonUtils.OBJECT_MAPPER.readerFor(Error.class).readValue(tuple._2.string())
      ))
      .getOrElse(LifxErrorException.GENERIC_LIFX_ERROR);
  }
}
