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

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(ToggleLightsPowerInput input) {
    var lightSelector = input.getLightSelector();
    var duration = input.getDuration();

    log.info("Toggle lights power of {} in {} seconds", lightSelector.identifier(), duration.getSeconds());
    return new CallExecutor<>(lifxApi.togglePower(lightSelector, TogglePower.in(duration))).execute()
      .map(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }
}
