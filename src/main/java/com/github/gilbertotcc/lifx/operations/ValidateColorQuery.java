package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ValidateColorQuery implements Operation<ValidateColorInput, ValidateColorOutput, LifxCallException> {

  private final LifxApi lifxApi;

  @Override
  public Either<LifxCallException, ValidateColorOutput> execute(ValidateColorInput input) {
    var colorString = input.getColorString();
    log.info("Validate color {}", colorString);
    return new CallExecutor<>(lifxApi.validateColor(colorString))
      .execute(ValidateColorOutput::new);
  }
}
