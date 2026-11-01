package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;

public interface Operation<I, O> {

  /**
   * Synchronously executes the operation with the supplied input.
   *
   * @return {@link LifxResult.Success} with the result or {@link LifxResult.Failure} in case of failure.
   */
  LifxResult<O> execute(I input);
}
