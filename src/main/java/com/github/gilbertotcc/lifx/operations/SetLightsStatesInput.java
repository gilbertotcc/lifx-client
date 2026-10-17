package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.State;
import lombok.AccessLevel;
import lombok.Getter;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Getter(AccessLevel.PACKAGE)
public class SetLightsStatesInput extends LightsStatesDto {

  /**
   * Creates a batch state request, retaining the supplied list without copying or validating it.
   *
   * @param defaultState defaults to send with the per-selector states, or null to omit
   */
  public SetLightsStatesInput(List<LightsState> lightsStates, @Nullable State defaultState, boolean fast) {
    super(lightsStates, defaultState, fast);
  }
}
