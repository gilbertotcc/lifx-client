package com.github.gilbertotcc.lifx.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.gilbertotcc.lifx.models.LightsSelector;

public class LightsSelectorSerializer extends JsonSerializer<LightsSelector> {

    @Override
    public void serialize(final LightsSelector lightsSelector, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(lightsSelector.getIdentifier());
    }
}
