package com.github.gilbertotcc.lifx.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

public record Cycle(
  @JsonProperty("states")
  List<State> states,

  @JsonProperty("defaults")
  @Nullable State defaultState,

  @JsonProperty("direction")
  @Nullable Direction direction
) {

  public enum Direction {
    FORWARD("forward"), BACKWARD("backward");

    public final String label;

    Direction(final String label) {
      this.label = label;
    }
  }

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
