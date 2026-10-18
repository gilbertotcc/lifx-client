package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ValidateColorQuery implements Operation<ValidateColorInput, ValidateColorOutput> {

  private final LifxApi lifxApi;

  /**
   * Synchronously asks the API to parse the supplied color string.
   *
   * @return the parsed color on the right, possibly null if a successful response has no body,
   *     or a call exception on the left for HTTP or I/O failures
   */
  @Override
  public Either<LifxCallException, ValidateColorOutput> execute(ValidateColorInput input) {
    var colorString = input.colorString();
    log.info("Validate color {}", colorString);
    return new CallExecutor<>(lifxApi.validateColor(colorString)).execute()
      .map(ValidateColorOutput::new);
  }
}
