package com.github.gilbertotcc.lifx.util.json;

import java.util.Optional;

import com.github.gilbertotcc.lifx.models.Power;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

class PowerDeserializer extends ValueDeserializer<Power> {

  /**
   * Reads a case-insensitive power label without trimming whitespace.
   *
   * @return the matching power, or null for null or blank parser text
   * @throws IllegalArgumentException if the nonblank text is not a known power label
   * @throws tools.jackson.core.JacksonException if reading the token text fails
   */
  @Override
  public @Nullable Power deserialize(final JsonParser jsonParser,
                                     final DeserializationContext deserializationContext) {
    return Optional.ofNullable(jsonParser.getString())
      .filter(value -> !value.isBlank())
      .map(Power::byLabel)
      .orElse(null);
  }
}
