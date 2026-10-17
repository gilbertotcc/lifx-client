package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

@Getter
@ToString(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class State extends AbstractState {

  /**
   * Creates a state update without local validation or defaults.
   * Null properties are omitted from JSON; the transition duration is serialized in whole seconds.
   */
  @Builder
  public State(final @Nullable Power power,
               final @Nullable String color,
               final @Nullable Double brightness,
               final @Nullable Duration duration,
               final @Nullable Double infrared,
               final @Nullable Boolean fast) {
    super(power, color, brightness, duration, infrared, fast);
  }
}
