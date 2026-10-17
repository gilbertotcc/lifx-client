package com.github.gilbertotcc.lifx.models.serializer;

import java.time.Duration;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class DurationSerializer extends ValueSerializer<Duration> {

  @Override
  public void serialize(final Duration duration,
                        final JsonGenerator jsonGenerator,
                        final SerializationContext serializationContext) {
    final double durationInSeconds = (double) duration.toSeconds();
    jsonGenerator.writeNumber(durationInSeconds);
  }
}
