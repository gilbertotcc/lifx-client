package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.State;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class SetLightsStateInput {

  private final LightSelector lightSelector;

  private final State state;
}
