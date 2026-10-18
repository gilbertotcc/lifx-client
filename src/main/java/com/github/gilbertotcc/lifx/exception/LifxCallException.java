package com.github.gilbertotcc.lifx.exception;

import retrofit2.Call;

import java.io.Serial;

public class LifxCallException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 8909863162390335463L;

  public static LifxCallException with(final Call<?> call, final Throwable cause) {
    return new LifxCallException(
      "Call %s %s failed with cause %s".formatted(call.request().method(), call.request().url(), cause.getMessage()),
      cause
    );
  }

  LifxCallException(String message, Throwable cause) {
    super(message, cause);
  }
}
