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
  implements Operation<TransitToNextStateInput, CommandOutput<List<Result>>, LifxCallException> {

  private final LifxApi lifxApi;

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(TransitToNextStateInput input) {
    LightSelector lightSelector = input.getLightSelector();
    Cycle cycle = input.getCycle();

    log.info("Transit to next state of {}", lightSelector.identifier());
    return new CallExecutor<>(lifxApi.cycle(lightSelector, cycle))
      .execute(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }
}
