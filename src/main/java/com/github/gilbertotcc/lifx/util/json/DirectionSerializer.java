package com.github.gilbertotcc.lifx.util.json;

import com.github.gilbertotcc.lifx.models.Cycle;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

class DirectionSerializer extends ValueSerializer<Cycle.Direction> {

  @Override
  public void serialize(final Cycle.Direction direction,
                        final JsonGenerator jsonGenerator,
                        final SerializationContext serializationContext) {
    jsonGenerator.writeString(direction.label);
  }
}
