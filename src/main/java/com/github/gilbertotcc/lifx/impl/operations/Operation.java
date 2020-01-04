package com.github.gilbertotcc.lifx.impl.operations;

public interface Operation<I, O extends OperationResult<?>> {

  O execute(I input);
}
