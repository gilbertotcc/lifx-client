package com.github.gilbertotcc.lifx.exception;

import static java.lang.String.format;

import lombok.Getter;
import retrofit2.Call;

@Getter
public class LifxCallException extends RuntimeException {

  private static final long serialVersionUID = 8909863162390335463L;

  private final Call<?> call;

  public LifxCallException(final Call<?> call, Throwable cause) {
    super(
      format("Call %s %s failed with cause %s", call.request().method(), call.request().url(), cause),
      cause
    );
    this.call = call;
  }
}
