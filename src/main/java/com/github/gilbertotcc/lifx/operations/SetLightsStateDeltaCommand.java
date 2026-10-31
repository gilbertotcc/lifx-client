package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.ResultsDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SetLightsStateDeltaCommand
  implements Operation<SetLightsStateDeltaInput, OperationOutput<List<Result>>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests relative state changes for the selected lights.
   *
   * @return per-light results on the right (an {@code Empty} if a successful response has no body),
   *     or a call exception on the left for HTTP or I/O failures
   */
  @Override
  public Either<LifxCallException, OperationOutput<List<Result>>> execute(SetLightsStateDeltaInput input) {
    var lightSelector = input.lightSelector();
    var stateDelta = input.stateDelta();

    log.info("Set lights state delta of {} to {}",
      lightSelector.identifier(), stateDelta);
    return new CallExecutor<>(lifxApi.setLightsStateDelta(lightSelector, stateDelta)).execute()
      .map(OperationOutput.from(ResultsDto::results));
  }
}
