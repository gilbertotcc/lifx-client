package com.github.gilbertotcc.lifx.models.deserializer;

import java.util.Optional;

import com.github.gilbertotcc.lifx.models.Result;
import org.jspecify.annotations.Nullable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class ResultStatusDeserializer extends ValueDeserializer<Result.Status> {

  /**
   * Reads a case-sensitive result status label without trimming whitespace.
   *
   * @return the matching status, or null for null or blank parser text
   * @throws IllegalArgumentException if the nonblank text is not a known status label
   * @throws tools.jackson.core.JacksonException if reading the token text fails
   */
  @Override
  public Result.@Nullable Status deserialize(final JsonParser jsonParser,
                                             final DeserializationContext deserializationContext) {
    return Optional.ofNullable(jsonParser.getString())
      .filter(value -> !value.isBlank())
      .map(Result.Status::byLabel)
      .orElse(null);
  }
}
