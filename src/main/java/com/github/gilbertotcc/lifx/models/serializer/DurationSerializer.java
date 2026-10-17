package com.github.gilbertotcc.lifx.models.serializer;

import java.time.Duration;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class DurationSerializer extends ValueSerializer<Duration> {

  /**
   * Writes the duration's whole-second component as a JSON number, ignoring nanoseconds.
   * For example, 1.5 seconds becomes 1.0, and -0.5 seconds becomes -1.0.
   *
   * @throws tools.jackson.core.JacksonException if writing the number fails
   */
  @Override
  public void serialize(final Duration duration,
                        final JsonGenerator jsonGenerator,
                        final SerializationContext serializationContext) {
    final double durationInSeconds = (double) duration.toSeconds();
    jsonGenerator.writeNumber(durationInSeconds);
  }
}
