package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.Color;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ValidateColorQuery implements Operation<ValidateColorInput, Color> {

  private final LifxApi lifxApi;

  /**
   * Synchronously asks the API to parse the supplied color string.
   *
   * @return {@link LifxResult.Success.Some} with the parsed color, or {@link LifxResult.Failure} with a call
   *     exception for HTTP or I/O failures or a successful response without a body. Never
   *     {@link LifxResult.Success.None}
   */
  @Override
  public LifxResult<Color> execute(ValidateColorInput input) {
    var colorString = input.colorString();
    log.info("Validate color {}", colorString);
    return OperationCalls.executeQuery(lifxApi.validateColor(colorString));
  }
}
