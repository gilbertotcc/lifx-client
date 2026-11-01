package com.github.gilbertotcc.lifx.util.json;

import com.github.gilbertotcc.lifx.models.LightSelector;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

class LightSelectorSerializer extends ValueSerializer<LightSelector> {

  @Override
  public void serialize(final LightSelector lightSelector,
                        final JsonGenerator jsonGenerator,
                        final SerializationContext serializationContext) {
    jsonGenerator.writeString(lightSelector.identifier());
  }
}
