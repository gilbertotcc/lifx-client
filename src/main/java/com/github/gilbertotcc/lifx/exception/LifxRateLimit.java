package com.github.gilbertotcc.lifx.exception;

import io.vavr.control.Try;
import okhttp3.Headers;
import org.jspecify.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.function.Function;

/**
 * Rate-limit data that LIFX sends in the {@code X-RateLimit-*} response headers. A component is null when its header is
 * absent or malformed.
 *
 * @param limit total number of requests allowed per 60 second window
 * @param remaining number of requests still allowed in the current window
 * @param reset when the next window begins
 */
public record LifxRateLimit(
  @Nullable Integer limit,
  @Nullable Integer remaining,
  @Nullable Instant reset
) implements Serializable {

  @Serial
  private static final long serialVersionUID = 4127706382915460271L;

  /**
   * Reads the rate-limit headers, without ever failing on a malformed value.
   *
   * @return the rate-limit data, or null if none of the three headers has a usable value
   */
  static @Nullable LifxRateLimit from(final Headers headers) {
    var limit = parse(headers, "X-RateLimit-Limit", Integer::valueOf);
    var remaining = parse(headers, "X-RateLimit-Remaining", Integer::valueOf);
    var reset = parse(headers, "X-RateLimit-Reset", value -> Instant.ofEpochSecond(Long.parseLong(value)));
    return limit == null && remaining == null && reset == null ? null : new LifxRateLimit(limit, remaining, reset);
  }

  private static <T> @Nullable T parse(final Headers headers, final String name, final Function<String, T> parser) {
    var value = headers.get(name);
    return value == null ? null : Try.of(() -> parser.apply(value.trim())).getOrNull();
  }
}
