package com.github.gilbertotcc.lifx.operations;

import io.vavr.control.Either;

public interface Operation<I, O, E extends Throwable> {

  Either<E, O> execute(I input);
}
