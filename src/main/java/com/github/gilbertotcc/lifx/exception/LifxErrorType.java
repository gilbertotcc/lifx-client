package com.github.gilbertotcc.lifx.exception;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public enum LifxErrorType {

  BAD_REQUEST("Request was invalid."),
  UNAUTHORIZED("Bad access token."),
  PERMISSION_DENIED("Bad OAuth scope."),
  NOT_FOUND("Selector did not match any lights."),
  NOT_PROCESSABLE_ENTITY("Missing or malformed parameters."),
  UPGRADE_REQUIRED("HTTP was used to make the request instead of HTTPS."),
  TOO_MANY_REQUESTS("The request exceeded a rate limit. See the Rate Limits section."),
  SERVER_ERROR("Something went wrong on LIFX's end."),
  UNKNOWN("Unknown error.");

  @NonNull
  final String reason;

  /**
   * Finds the error type for an HTTP status code.
   *
   * @return the mapped type, or {@link #UNKNOWN} for any unmapped code
   */
  public static LifxErrorType byHttpCode(final int httpCode) {
    return switch (httpCode) {
      case 400                -> BAD_REQUEST;
      case 401                -> UNAUTHORIZED;
      case 403                -> PERMISSION_DENIED;
      case 404                -> NOT_FOUND;
      case 422                -> NOT_PROCESSABLE_ENTITY;
      case 426                -> UPGRADE_REQUIRED;
      case 429                -> TOO_MANY_REQUESTS;
      case 500, 502, 503, 523 -> SERVER_ERROR;
      default                 -> UNKNOWN;
    };
  }
}
