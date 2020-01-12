package com.github.gilbertotcc.lifx.operations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class ValidateColorInput {

  private final String colorString;
}
