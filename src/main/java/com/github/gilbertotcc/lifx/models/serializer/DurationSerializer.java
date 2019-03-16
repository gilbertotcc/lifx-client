package com.github.gilbertotcc.lifx.models.serializer;

import java.io.IOException;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DurationSerializer extends JsonSerializer<Duration> {

  @Override
  public void serialize(final Duration duration,
                        final JsonGenerator jsonGenerator,
                        final SerializerProvider serializerProvider)
    throws IOException {
    final double durationInSeconds = (double) duration.getSeconds();
    jsonGenerator.writeNumber(durationInSeconds);
  }
}
