package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.State;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

@Getter(AccessLevel.PACKAGE)
public class SetLightsStatesInput extends LightsStatesDto {

  public SetLightsStatesInput(List<LightsState> lightsStates, State defaultState, boolean fast) {
    super(lightsStates, defaultState, fast);
  }
}
