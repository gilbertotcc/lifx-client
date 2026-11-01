package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SetLightsStateDeltaCommand
  implements Operation<SetLightsStateDeltaInput, List<Result>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests relative state changes for the selected lights.
   *
   * @return {@link LifxResult.Success.Some} with the per-light results, {@link LifxResult.Success.None} if a
   *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
   *     failures
   */
  @Override
  public LifxResult<List<Result>> execute(SetLightsStateDeltaInput input) {
    var lightSelector = input.lightSelector();
    var stateDelta = input.stateDelta();

    log.info("Set lights state delta of {} to {}",
      lightSelector.identifier(), stateDelta);
    return OperationCalls.executeCommand(lifxApi.setLightsStateDelta(lightSelector, stateDelta));
  }
}
