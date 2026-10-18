package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import io.vavr.control.Either;

public interface Operation<I, O> {

  Either<LifxCallException, O> execute(I input);
}
