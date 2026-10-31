package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Color;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ValidateColorQuery implements Operation<ValidateColorInput, CommandOutput.SomeOutput<Color>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously asks the API to parse the supplied color string.
   *
   * @return the parsed color on the right, or a call exception on the left for HTTP or I/O failures or a
   *     successful response without a body
   */
  @Override
  public Either<LifxCallException, CommandOutput.SomeOutput<Color>> execute(ValidateColorInput input) {
    var colorString = input.colorString();
    log.info("Validate color {}", colorString);
    return new CallExecutor<>(lifxApi.validateColor(colorString)).executeExpectingBody()
      .map(CommandOutput.SomeOutput::new);
  }
}
