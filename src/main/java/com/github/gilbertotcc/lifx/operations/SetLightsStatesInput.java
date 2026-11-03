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
   * Creates a batch state request, retaining an unmodifiable copy of the supplied list.
   *
   * @param defaultState defaults to send with the per-selector states, or null to omit
   * @throws NullPointerException if the list or one of its elements is null
   */
  public SetLightsStatesInput(List<LightsState> lightsStates, @Nullable State defaultState, boolean fast) {
    super(List.copyOf(lightsStates), defaultState, fast);
  }
}
