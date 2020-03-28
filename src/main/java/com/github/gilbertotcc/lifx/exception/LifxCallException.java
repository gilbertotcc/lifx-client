package com.github.gilbertotcc.lifx.exception;

import static java.lang.String.format;

import retrofit2.Call;

public class LifxCallException extends RuntimeException {

  private static final long serialVersionUID = 8909863162390335463L;

  public static LifxCallException with(final Call<?> call, final Throwable cause) {
    return new LifxCallException(
      format("Call %s %s failed with cause %s", call.request().method(), call.request().url(), cause.getMessage()),
      cause
    );
  }

  LifxCallException(String message, Throwable cause) {
    super(message, cause);
  }
}
