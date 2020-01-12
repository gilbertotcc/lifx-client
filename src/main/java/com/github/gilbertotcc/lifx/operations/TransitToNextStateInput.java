package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class TransitToNextStateInput {

  private final LightSelector lightSelector;

  private final Cycle cycle;
}
