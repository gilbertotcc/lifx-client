package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;

public record TransitToNextStateInput(LightSelector lightSelector, Cycle cycle) {}
