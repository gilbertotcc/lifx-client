package com.github.gilbertotcc.lifx.operations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class CommandOutput<T> {

  private final T result;
}
