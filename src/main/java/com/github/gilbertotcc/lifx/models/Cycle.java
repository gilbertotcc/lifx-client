package com.github.gilbertotcc.lifx.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.gilbertotcc.lifx.models.serializer.DirectionSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cycle {

  public enum Direction {
    FORWARD("forward"), BACKWARD("backward");

    public final String label;

    Direction(final String label) {
      this.label = label;
    }
  }

  @JsonProperty("states")
  private List<State> states;

  @JsonProperty("defaults")
  private State defaultState;

  @JsonProperty("direction")
  @JsonSerialize(using = DirectionSerializer.class)
  private Direction direction;

  public static Cycle of(final List<State> states, final State defaultState, final Direction direction) {
    return new Cycle(states, defaultState, direction);
  }
}
