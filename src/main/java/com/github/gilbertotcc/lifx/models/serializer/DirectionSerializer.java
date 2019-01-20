package com.github.gilbertotcc.lifx.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.gilbertotcc.lifx.models.Cycle;

public class DirectionSerializer extends JsonSerializer<Cycle.Direction> {

  @Override
  public void serialize(final Cycle.Direction direction,
                        final JsonGenerator jsonGenerator,
                        final SerializerProvider serializerProvider)
    throws IOException {
    jsonGenerator.writeString(direction.label);
  }
}
