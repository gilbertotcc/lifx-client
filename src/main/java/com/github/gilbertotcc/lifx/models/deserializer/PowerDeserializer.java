package com.github.gilbertotcc.lifx.models.deserializer;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.gilbertotcc.lifx.models.Power;
import org.apache.commons.lang3.StringUtils;

public class PowerDeserializer extends JsonDeserializer<Power> {

  @Override
  public Power deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
    throws IOException {
    return Optional.ofNullable(jsonParser.getText())
      .filter(StringUtils::isNotBlank)
      .map(Power::byLabel)
      .orElse(null);
  }
}
