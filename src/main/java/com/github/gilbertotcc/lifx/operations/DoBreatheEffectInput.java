package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.LightSelector;

public record DoBreatheEffectInput(LightSelector lightSelector, BreatheEffect breatheEffect) {}
