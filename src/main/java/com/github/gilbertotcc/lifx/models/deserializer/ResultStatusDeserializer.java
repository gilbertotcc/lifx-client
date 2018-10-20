package com.github.gilbertotcc.lifx.models.deserializer;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.gilbertotcc.lifx.models.Results;
import org.apache.commons.lang3.StringUtils;

public class ResultStatusDeserializer extends JsonDeserializer<Results.Result.Status> {

    @Override
    public Results.Result.Status deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        return Optional.ofNullable(jsonParser.getText())
                .filter(StringUtils::isNotBlank)
                .map(Results.Result.Status::byLabel)
                .orElse(null);
    }
}
