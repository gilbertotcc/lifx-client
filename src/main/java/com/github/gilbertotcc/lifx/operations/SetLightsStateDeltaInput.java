package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.StateDelta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class SetLightsStateDeltaInput {

  private final LightSelector lightSelector;

  private final StateDelta stateDelta;
}
