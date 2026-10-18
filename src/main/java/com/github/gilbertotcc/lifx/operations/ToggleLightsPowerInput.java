package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;

import java.time.Duration;

public record ToggleLightsPowerInput(LightSelector lightSelector, Duration duration) {}
