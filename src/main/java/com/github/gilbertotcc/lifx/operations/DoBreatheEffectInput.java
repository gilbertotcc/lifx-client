package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.LightSelector;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class DoBreatheEffectInput {

  private final LightSelector lightSelector;

  private final BreatheEffect breatheEffect;
}
