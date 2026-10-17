package com.github.gilbertotcc.lifx.models.deserializer;

import java.util.Optional;

import com.github.gilbertotcc.lifx.models.Result;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class ResultStatusDeserializer extends ValueDeserializer<Result.Status> {

  @Override
  public Result.Status deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) {
    return Optional.ofNullable(jsonParser.getString())
      .filter(StringUtils::isNotBlank)
      .map(Result.Status::byLabel)
      .orElse(null);
  }
}
