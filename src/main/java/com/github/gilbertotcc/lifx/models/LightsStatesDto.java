package com.github.gilbertotcc.lifx.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.jspecify.annotations.Nullable;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LightsStatesDto {

  @Singular
  @JsonProperty("states")
  private List<LightsState> lightsStates;

  @JsonProperty("defaults")
  private @Nullable State defaultState;

  @JsonProperty("fast")
  private boolean fast;
}
