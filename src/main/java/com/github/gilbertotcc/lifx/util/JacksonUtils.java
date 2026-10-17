package com.github.gilbertotcc.lifx.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.UtilityClass;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@UtilityClass
public class JacksonUtils {

  public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
    .changeDefaultPropertyInclusion(inclusion ->
      JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
    .build();
}
