package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import com.github.gilbertotcc.lifx.models.ResultsDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DoPulseEffectCommand
  implements Operation<DoPulseEffectInput, OperationOutput<List<Result>>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a pulse effect for the selected lights.
   *
   * @return per-light results on the right (an {@code Empty} if a successful response has no body),
   *     or a call exception on the left for HTTP or I/O failures
   */
  @Override
  public Either<LifxCallException, OperationOutput<List<Result>>> execute(DoPulseEffectInput input) {
    LightSelector lightSelector = input.lightSelector();
    PulseEffect pulseEffect = input.pulseEffect();

    log.info("Do pulse effect with {}. Settings: {}",
      lightSelector.identifier(),
      pulseEffect);
    return new CallExecutor<>(lifxApi.pulseEffect(lightSelector, pulseEffect)).execute()
      .map(OperationOutput.from(ResultsDto::results));
  }
}
