package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Cycle;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.Result;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TransitToNextStateCommand
  implements Operation<TransitToNextStateInput, CommandOutput<List<Result>>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a transition to the next state in the supplied cycle for the selected lights.
   *
   * @return per-light results on the right, or a call exception on the left for HTTP or I/O failures
   * @throws NullPointerException if a successful response has no body
   */
  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(TransitToNextStateInput input) {
    LightSelector lightSelector = input.lightSelector();
    Cycle cycle = input.cycle();

    log.info("Transit to next state of {}", lightSelector.identifier());
    return new CallExecutor<>(lifxApi.cycle(lightSelector, cycle)).execute()
      .map(response -> {
        var results = response.results();
        return new CommandOutput<>(results);
      });
  }
}
