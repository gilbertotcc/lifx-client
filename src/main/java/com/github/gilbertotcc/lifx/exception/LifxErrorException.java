package com.github.gilbertotcc.lifx.exception;

import static com.github.gilbertotcc.lifx.util.JacksonUtils.OBJECT_MAPPER;

import com.github.gilbertotcc.lifx.models.Error;
import io.vavr.control.Try;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import retrofit2.Response;

import java.io.Serial;
import java.util.Objects;

@Getter
public class LifxErrorException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1185407732054355941L;

  private final @Nullable LifxErrorType type;
  private final @Nullable Error error;

  /**
   * Builds an exception from the HTTP status and JSON error body, consuming the body.
   * Unmapped status codes use {@link LifxErrorType#UNKNOWN}.
   *
   * @return an exception containing the parsed error, or a fresh "Unknown error" exception with null type
   *     and error if the body is absent, cannot be read, or cannot be parsed
   */
  public static LifxErrorException from(Response<?> response) {
    return Try.of(() -> {
      var errorType = LifxErrorType.byHttpCode(response.code());
      var errorBody = Objects.requireNonNull(response.errorBody());
      var error = OBJECT_MAPPER.readerFor(Error.class).<Error>readValue(errorBody.string());
      return new LifxErrorException(errorType, error);
    })
      .getOrElse(LifxErrorException::new);
  }

  public LifxErrorException(final LifxErrorType type, final Error error) {
    super("Error %s (%s): %s".formatted(type.name(), type.reason, error.getErrorMessage()));
    this.type = type;
    this.error = error;
  }

  private LifxErrorException() {
    super("Unknown error");
    this.type = null;
    this.error = null;
  }
}
