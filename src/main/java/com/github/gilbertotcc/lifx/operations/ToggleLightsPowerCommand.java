package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.TogglePower;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ToggleLightsPowerCommand
  implements Operation<ToggleLightsPowerInput, List<Result>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a power toggle for the selected lights.
   * The requested transition duration is serialized in whole seconds.
   *
   * @return {@link LifxResult.Success.Some} with the per-light results, {@link LifxResult.Success.None} if a
   *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
   *     failures
   */
  @Override
  public LifxResult<List<Result>> execute(ToggleLightsPowerInput input) {
    var lightSelector = input.lightSelector();
    var duration = input.duration();

    log.info("Toggle lights power of {} in {} seconds", lightSelector.identifier(), duration.toSeconds());
    return OperationCalls.executeCommand(lifxApi.togglePower(lightSelector, TogglePower.in(duration)));
  }
}
