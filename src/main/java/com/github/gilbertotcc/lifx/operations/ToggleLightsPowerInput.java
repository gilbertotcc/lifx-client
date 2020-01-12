package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.LightSelector;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class ToggleLightsPowerInput {

  private final LightSelector lightSelector;

  private final Duration duration;
}
