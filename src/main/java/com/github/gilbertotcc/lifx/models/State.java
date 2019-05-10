package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class State extends AbstractState {

  @Builder
  public State(final Power power,
               final String color,
               final Double brightness,
               final Duration duration,
               final Double infrared,
               final Boolean fast) {
    super(power, color, brightness, duration, infrared, fast);
  }
}
