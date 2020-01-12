package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.models.Light;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ListLightsOutput {

  private final List<Light> lights;
}
