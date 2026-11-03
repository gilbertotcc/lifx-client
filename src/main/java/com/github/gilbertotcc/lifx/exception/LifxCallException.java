package com.github.gilbertotcc.lifx.exception;

import io.vavr.control.Try;
import retrofit2.Call;

import java.io.Serial;

public class LifxCallException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 8909863162390335463L;

  /**
   * Creates an exception that retains the cause and identifies the failed request by HTTP method and URL.
   * The method and URL are left out if the request itself cannot be built, since {@link Call#request()} then throws.
   */
  public static LifxCallException with(final Call<?> call, final Throwable cause) {
    var target = Try.of(call::request)
      .map(request -> "%s %s ".formatted(request.method(), request.url()))
      .getOrElse("");
    return new LifxCallException("Call %sfailed with cause %s".formatted(target, cause.getMessage()), cause);
  }

  LifxCallException(String message, Throwable cause) {
    super(message, cause);
  }
}
