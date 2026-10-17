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
public class SetLightsStateCommand
  implements Operation<SetLightsStateInput, CommandOutput<List<Result>>, LifxCallException> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a state update for the selected lights.
   *
   * @return per-light results on the right, or a call exception on the left for HTTP or I/O failures
   * @throws NullPointerException if a successful response has no body
   */
  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(SetLightsStateInput input) {
    var lightSelector = input.getLightSelector();
    var state = input.getState();

    log.info("Set lights state of {} to {}",
      lightSelector.identifier(),
      state
    );
    return new CallExecutor<>(lifxApi.setLightsState(lightSelector, state)).execute()
      .map(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }
}
