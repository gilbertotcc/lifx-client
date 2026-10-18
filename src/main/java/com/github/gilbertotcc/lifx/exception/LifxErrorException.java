package com.github.gilbertotcc.lifx.exception;

import static com.github.gilbertotcc.lifx.util.JacksonUtils.OBJECT_MAPPER;

import com.github.gilbertotcc.lifx.models.ErrorModel;
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

  private final LifxErrorType type;
  private final @Nullable ErrorModel errorModel;

  /**
   * Builds an exception from the HTTP status and JSON error body, consuming the body.
   * Unmapped status codes use {@link LifxErrorType#UNKNOWN}.
   *
   * @return an exception with the type mapped from the status code, and a null error model if the body is absent,
   *     cannot be read, or cannot be parsed
   * @throws NullPointerException if the response is null
   */
  public static LifxErrorException from(Response<?> response) {
    var errorType = LifxErrorType.byHttpCode(response.code());
    var errorModel = Try.of(() -> {
      var errorBody = Objects.requireNonNull(response.errorBody());
      return OBJECT_MAPPER.readerFor(ErrorModel.class).<ErrorModel>readValue(errorBody.string());
    }).getOrNull();

    return new LifxErrorException(errorType, errorModel);
  }

  /**
   * Creates an exception retaining the error type and parsed error.
   * The message includes the type and reason, plus the parsed error message when non-null.
   *
   * @throws NullPointerException if the type is null
   */
  public LifxErrorException(final LifxErrorType type, final @Nullable ErrorModel errorModel) {
    super(message(type, errorModel));
    this.type = type;
    this.errorModel = errorModel;
  }

  /**
   * Formats the error type and reason, appending the parsed error message when non-null.
   *
   * @throws NullPointerException if the type is null
   */
  private static String message(final LifxErrorType type, final @Nullable ErrorModel errorModel) {
    var summary = "Error %s (%s)".formatted(type.name(), type.reason);
    var errorMessage = errorModel == null ? null : errorModel.errorMessage();
    return errorMessage == null ? summary : "%s: %s".formatted(summary, errorMessage);
  }
}
