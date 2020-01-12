package com.github.gilbertotcc.lifx.exception;

import static java.lang.String.format;

import retrofit2.Call;

public class LifxCallException extends RuntimeException {

  private static final long serialVersionUID = 8909863162390335463L;

  private final transient Call call;

  /**
   * If not {@code null}, return the call that failed.
   *
   * @return the instance of {@link Call} that failed.
   *
   * @deprecated This method might return an optional value in further releases.
   */
  @Deprecated(since = "1.2.0", forRemoval = true)
  public Call getCall() {
    return call;
  }

  public LifxCallException(final Call call, Throwable cause) {
    super(
      format("Call %s %s failed with cause %s", call.request().method(), call.request().url(), cause.getMessage()),
      cause
    );
    this.call = call;
  }

  public LifxCallException(Throwable cause) {
    super(format("Call failed with cause %s", cause.getMessage()), cause);
    this.call = null;
  }
}
