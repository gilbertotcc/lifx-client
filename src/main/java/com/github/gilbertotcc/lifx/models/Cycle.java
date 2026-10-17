package com.github.gilbertotcc.lifx.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gilbertotcc.lifx.models.serializer.DirectionSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.annotation.JsonSerialize;

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
  private @Nullable State defaultState;

  @JsonProperty("direction")
  @JsonSerialize(using = DirectionSerializer.class)
  private @Nullable Direction direction;

  /**
   * Creates cycle settings, retaining the supplied state list without copying or validating it.
   *
   * @param defaultState defaults to send with the states, or null to omit
   * @param direction cycle direction to send, or null to omit
   */
  public static Cycle of(final List<State> states, final @Nullable State defaultState,
                         final @Nullable Direction direction) {
    return new Cycle(states, defaultState, direction);
  }
}
