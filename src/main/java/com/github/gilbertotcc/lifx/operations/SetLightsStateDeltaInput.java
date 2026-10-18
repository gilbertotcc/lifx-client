package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.StateDelta;

public record SetLightsStateDeltaInput(LightSelector lightSelector, StateDelta stateDelta) {}
