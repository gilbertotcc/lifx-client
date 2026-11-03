package com.github.gilbertotcc.lifx.exception;

import static com.github.gilbertotcc.lifx.util.json.JacksonUtils.OBJECT_MAPPER;

import com.github.gilbertotcc.lifx.models.ErrorModel;
import io.vavr.control.Try;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import retrofit2.Response;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;

@Getter
public class LifxErrorException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1185407732054355941L;

  private final int statusCode;
  private final LifxErrorType type;
  private final @Nullable ErrorModel errorModel;
  private final @Nullable String rawBody;
  private final @Nullable LifxRateLimit rateLimit;

  /**
   * Builds an exception from the HTTP status, headers and error body, consuming the body.
   * Unmapped status codes use {@link LifxErrorType#UNKNOWN}.
   *
   * @return an exception with the status code, the type mapped from it, the rate-limit data if any, the raw body
   *     (null if absent or unreadable), and a null error model if the body is absent, cannot be read, or cannot be
   *     parsed
   * @throws NullPointerException if the response is null
   */
  public static LifxErrorException from(Response<?> response) {
    var rawBody = Try.of(() -> Objects.requireNonNull(response.errorBody()).string()).getOrNull();
    var errorModel = Optional.ofNullable(rawBody)
      .map(nonEmptyBody -> Try.of(() -> OBJECT_MAPPER.readerFor(ErrorModel.class).<ErrorModel>readValue(nonEmptyBody)))
      .flatMap(Try::toJavaOptional)
      .orElse(null);

    return new LifxErrorException(response.code(), errorModel, rawBody, LifxRateLimit.from(response.headers()));
  }

  /**
   * Creates an exception retaining the response data, with the error type derived from the status code.
   * The message includes the type, status code and reason, plus the parsed error message when non-null. It leaves out
   * the raw body.
   *
   * @param statusCode the HTTP status code
   * @param errorModel the parsed error body, or null if it is not available
   * @param rawBody the error body as received, or null if it is not available
   * @param rateLimit the rate-limit data of the response, or null if it is not available
   */
  public LifxErrorException(final int statusCode,
                            final @Nullable ErrorModel errorModel,
                            final @Nullable String rawBody,
                            final @Nullable LifxRateLimit rateLimit) {
    super(message(statusCode, errorModel));
    this.statusCode = statusCode;
    this.type = LifxErrorType.byHttpCode(statusCode);
    this.errorModel = errorModel;
    this.rawBody = rawBody;
    this.rateLimit = rateLimit;
  }

  /**
   * Formats the error type, status code and reason, appending the parsed error message when non-null.
   */
  private static String message(final int statusCode, final @Nullable ErrorModel errorModel) {
    var type = LifxErrorType.byHttpCode(statusCode);
    var summary = "Error %s, HTTP %d (%s)".formatted(type.name(), statusCode, type.reason);
    return Optional.ofNullable(errorModel)
      .map(ErrorModel::errorMessage)
      .map(errorMessage -> "%s: %s".formatted(summary, errorMessage))
      .orElse(summary);
  }
}
