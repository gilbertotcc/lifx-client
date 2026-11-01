package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SetLightsStateCommand
  implements Operation<SetLightsStateInput, List<Result>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a state update for the selected lights.
   *
   * @return {@link LifxResult.Success.Some} with the per-light results, {@link LifxResult.Success.None} if a
   *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
   *     failures
   */
  @Override
  public LifxResult<List<Result>> execute(SetLightsStateInput input) {
    var lightSelector = input.lightSelector();
    var state = input.state();

    log.info("Set lights state of {} to {}",
      lightSelector.identifier(),
      state
    );
    return OperationCalls.executeCommand(lifxApi.setLightsState(lightSelector, state));
  }
}
