package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.exception.LifxCallException;
import io.vavr.control.Either;

public interface Operation<I, O> {

  /**
   * Synchronously executes the operation with the supplied input.
   *
   * @return the output on the right, or a call exception on the left for HTTP or I/O failures
   */
  Either<LifxCallException, O> execute(I input);
}
