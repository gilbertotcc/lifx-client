package com.github.gilbertotcc.lifx.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.UtilityClass;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@UtilityClass
public class JacksonUtils {

  public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
    .changeDefaultPropertyInclusion(inclusion ->
      JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
    // Jackson 3 enables it by default: keep reading null primitives (e.g. a missing kelvin) as 0, like 2.x
    .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
    .build();
}
