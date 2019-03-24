package com.github.gilbertotcc.lifx.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.gilbertotcc.lifx.models.LightSelector;

public class LightsSelectorSerializer extends JsonSerializer<LightSelector> {

  @Override
  public void serialize(final LightSelector lightsSelector,
                        final JsonGenerator jsonGenerator,
                        final SerializerProvider serializerProvider)
    throws IOException {
    jsonGenerator.writeString(lightsSelector.getIdentifier());
  }
}
