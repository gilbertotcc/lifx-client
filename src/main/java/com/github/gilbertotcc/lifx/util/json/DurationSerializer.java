package com.github.gilbertotcc.lifx.util.json;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

class DurationSerializer extends ValueSerializer<Duration> {

  /**
   * Writes the non-negative duration in seconds as a JSON number with one decimal place, dropping further digits.
   * For example, 1.15 seconds becomes 1.1, 1.2 seconds stays 1.2, and 0.56 seconds becomes 0.5.
   * Durations shorter than 0.1 seconds become 0.0.
   *
   * @throws ArithmeticException if the duration in milliseconds overflows a long; the mapper reports it to its caller
   *     as a {@link tools.jackson.core.JacksonException}
   * @throws tools.jackson.core.JacksonException if writing the number fails
   */
  @Override
  public void serialize(final Duration duration,
                        final JsonGenerator jsonGenerator,
                        final SerializationContext serializationContext) {
    final BigDecimal durationInSeconds = BigDecimal.valueOf(duration.toMillis(), 3).setScale(1, RoundingMode.DOWN);
    jsonGenerator.writeNumber(durationInSeconds);
  }
}
