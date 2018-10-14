package com.github.gilbertotcc.lifx.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.gilbertotcc.lifx.models.Power;

public class PowerSerializer extends JsonSerializer<Power> {

    @Override
    public void serialize(final Power power, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(power.label);
    }
}
