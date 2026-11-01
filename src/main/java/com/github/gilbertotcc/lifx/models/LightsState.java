package com.github.gilbertotcc.lifx.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

@Getter
public class LightsState extends AbstractState {

  @JsonProperty("selector")
  private LightSelector lightSelector;

  /**
   * Creates a state update for the selected lights without local validation or defaults.
   * Null state properties are omitted from JSON; the transition duration is serialized in whole seconds.
   */
  @Builder
  public LightsState(final LightSelector lightSelector,
                     final @Nullable Power power,
                     final @Nullable String color,
                     final @Nullable Double brightness,
                     final @Nullable Duration duration,
                     final @Nullable Double infrared,
                     final @Nullable Boolean fast) {
    super(power, color, brightness, duration, infrared, fast);
    this.lightSelector = lightSelector;
  }

  public static LightsState of(final LightSelector lightSelector, final State state) {
    return LightsState.builder()
      .lightSelector(lightSelector)
      .power(state.getPower())
      .color(state.getColor())
      .brightness(state.getBrightness())
      .duration(state.getDuration())
      .infrared(state.getInfrared())
      .fast(state.getFast())
      .build();
  }
}
