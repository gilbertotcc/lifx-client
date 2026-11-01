package com.github.gilbertotcc.lifx.util.json;

import com.github.gilbertotcc.lifx.models.Power;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

class PowerSerializer extends ValueSerializer<Power> {

  @Override
  public void serialize(final Power power,
                        final JsonGenerator jsonGenerator,
                        final SerializationContext serializationContext) {
    jsonGenerator.writeString(power.label);
  }
}
