package com.github.gilbertotcc.lifx.exception;

import static java.lang.String.format;

import com.github.gilbertotcc.lifx.models.Error;
import lombok.Getter;

@Getter
public class LifxErrorException extends RuntimeException {

  private static final long serialVersionUID = -1185407732054355941L;

  public static final LifxErrorException GENERIC_LIFX_ERROR = new LifxErrorException();

  private LifxErrorType type;
  private Error error;

  public LifxErrorException(final LifxErrorType type, final Error error) {
    super(format("Error %s (%s). %s. %s", type.name(), type.reason, error.getErrorMessage(), error.getDetails()));
    this.type = type;
    this.error = error;
  }

  private LifxErrorException() {
    super("Unknown error");
  }
}
