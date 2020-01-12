package com.github.gilbertotcc.lifx.exception;

import static com.github.gilbertotcc.lifx.util.JacksonUtils.OBJECT_MAPPER;
import static java.lang.String.format;

import com.github.gilbertotcc.lifx.models.Error;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import lombok.Getter;
import retrofit2.Response;

@Getter
public class LifxErrorException extends RuntimeException {

  private static final long serialVersionUID = -1185407732054355941L;

  private static final LifxErrorException GENERIC_LIFX_ERROR = new LifxErrorException();

  private final LifxErrorType type;
  private final Error error;

  public static LifxErrorException from(Response<?> response) {
    return Try.of(() -> new Tuple2<>(response.code(), response.errorBody()))
      .mapTry(tuple -> {
        var errorType = LifxErrorType.byHttpCode(tuple._1).orElse(LifxErrorType.UNKNOWN);
        var error = OBJECT_MAPPER.readerFor(Error.class).<Error>readValue(tuple._2.string());
        return new LifxErrorException(errorType, error);
      })
      .getOrElse(GENERIC_LIFX_ERROR);
  }

  public LifxErrorException(final LifxErrorType type, final Error error) {
    super(format("Error %s (%s): %s", type.name(), type.reason, error.getErrorMessage()));
    this.type = type;
    this.error = error;
  }

  private LifxErrorException() {
    super("Unknown error");
    this.type = null;
    this.error = null;
  }
}
