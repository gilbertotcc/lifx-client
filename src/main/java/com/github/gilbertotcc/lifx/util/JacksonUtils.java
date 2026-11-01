package com.github.gilbertotcc.lifx.util;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.Power;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.deserializer.PowerDeserializer;
import com.github.gilbertotcc.lifx.models.deserializer.ResultStatusDeserializer;
import com.github.gilbertotcc.lifx.models.serializer.DirectionSerializer;
import com.github.gilbertotcc.lifx.models.serializer.DurationSerializer;
import com.github.gilbertotcc.lifx.models.serializer.LightSelectorSerializer;
import com.github.gilbertotcc.lifx.models.serializer.PowerSerializer;
import lombok.experimental.UtilityClass;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

@UtilityClass
public class JacksonUtils {

  // Each (de)serializer applies to every property of its type, so models carry no Jackson databind annotations
  private static final SimpleModule LIFX_MODULE = new SimpleModule("lifx")
    .addSerializer(Duration.class, new DurationSerializer())
    .addSerializer(Power.class, new PowerSerializer())
    .addSerializer(Cycle.Direction.class, new DirectionSerializer())
    .addSerializer(LightSelector.class, new LightSelectorSerializer())
    .addDeserializer(Power.class, new PowerDeserializer())
    .addDeserializer(Result.Status.class, new ResultStatusDeserializer());

  public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
    .addModule(LIFX_MODULE)
    .changeDefaultPropertyInclusion(inclusion ->
      JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
    .build();
}
