package com.github.gilbertotcc.lifx.exception;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public enum LifxErrorType {

  BAD_REQUEST(400, "Request was invalid."),
  UNAUTHORIZED(401, "Bad access token."),
  PERMISSION_DENIED(403, "Bad OAuth scope."),
  NOT_FOUND(404, "Selector did not match any lights."),
  NOT_PROCESSABLE_ENTITY(422, "Missing or malformed parameters."),
  UPGRADE_REQUIRED(426, "HTTP was used to make the request instead of HTTPS."),
  TOO_MANY_REQUESTS(429, "The request exceeded a rate limit. See the Rate Limits section."),
  SERVER_ERROR(asList(500, 502, 503, 523), "Something went wrong on LIFX's end."),
  UNKNOWN(emptyList(), "Unknown error.");

  @NonNull
  final List<Integer> httpCodes;

  @NonNull
  final String reason;

  LifxErrorType(final Integer httpCode,
                final String reason) {
    this.httpCodes = unmodifiableList(singletonList(httpCode));
    this.reason = reason;
  }

  public static Optional<LifxErrorType> byHttpCode(final int httpCode) {
    return Stream.of(values())
      .filter(errorType -> errorType.httpCodes.contains(httpCode))
      .findFirst();
  }
}


