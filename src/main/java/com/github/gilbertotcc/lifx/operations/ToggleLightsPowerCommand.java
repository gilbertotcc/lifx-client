package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.TogglePower;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ToggleLightsPowerCommand
  implements Operation<ToggleLightsPowerInput, CommandOutput<List<Result>>, LifxCallException> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a power toggle for the selected lights.
   * The requested transition duration is serialized in whole seconds.
   *
   * @return per-light results on the right, or a call exception on the left for HTTP or I/O failures
   * @throws NullPointerException if a successful response has no body
   */
  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(ToggleLightsPowerInput input) {
    var lightSelector = input.getLightSelector();
    var duration = input.getDuration();

    log.info("Toggle lights power of {} in {} seconds", lightSelector.identifier(), duration.toSeconds());
    return new CallExecutor<>(lifxApi.togglePower(lightSelector, TogglePower.in(duration))).execute()
      .map(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }
}
