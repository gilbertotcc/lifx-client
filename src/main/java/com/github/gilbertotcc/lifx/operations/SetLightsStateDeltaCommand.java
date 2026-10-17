package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Result;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SetLightsStateDeltaCommand
  implements Operation<SetLightsStateDeltaInput, CommandOutput<List<Result>>, LifxCallException> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests relative state changes for the selected lights.
   *
   * @return per-light results on the right, or a call exception on the left for HTTP or I/O failures
   * @throws NullPointerException if a successful response has no body
   */
  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(SetLightsStateDeltaInput input) {
    var lightSelector = input.getLightSelector();
    var stateDelta = input.getStateDelta();

    log.info("Set lights state delta of {} to {}",
      lightSelector.identifier(), stateDelta);
    return new CallExecutor<>(lifxApi.setLightsStateDelta(lightSelector, stateDelta)).execute()
      .map(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }
}
