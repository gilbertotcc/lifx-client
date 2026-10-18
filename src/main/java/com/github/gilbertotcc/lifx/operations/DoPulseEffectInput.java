package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.PulseEffect;

public record DoPulseEffectInput(LightSelector lightSelector, PulseEffect pulseEffect) {}
