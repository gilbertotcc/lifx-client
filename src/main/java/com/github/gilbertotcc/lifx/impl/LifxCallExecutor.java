package com.github.gilbertotcc.lifx.impl;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import com.github.gilbertotcc.lifx.exception.LifxErrorException;
import com.github.gilbertotcc.lifx.exception.LifxErrorType;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.models.Error;
import com.github.gilbertotcc.lifx.util.JacksonUtils;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Value(staticConstructor = "of")
class LifxCallExecutor<T> {

  private Call<T> call;

  T getResponse() {
    try {
      final Response<T> response = call.execute();
      if (response.isSuccessful()) {
        return response.body();
      }
      throw lifxErrorExceptionFrom(response);
    } catch (IOException e) {
      throw new LifxCallException(call, e);
    }
  }

  private static LifxErrorException lifxErrorExceptionFrom(final Response<?> response) {
    return Optional.ofNullable(response.errorBody())
      .map(toError())
      .map(toLifxErrorExceptionWithHttpCode(response.code()))
      .orElse(LifxErrorException.GENERIC_LIFX_ERROR);
  }

  private static Function<ResponseBody, Error> toError() {
    return errorResponseBody -> {
      try {
        return JacksonUtils.OBJECT_MAPPER.readerFor(Error.class).readValue(errorResponseBody.string());
      } catch (IOException e) {
        return null;
      }
    };
  }

  private static Function<Error, LifxErrorException> toLifxErrorExceptionWithHttpCode(final int code) {
    return error ->
      LifxErrorType.byHttpCode(code)
        .map(errorType -> new LifxErrorException(errorType, error))
        .orElse(null);
  }
}
