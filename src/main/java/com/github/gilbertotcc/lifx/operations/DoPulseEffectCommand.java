package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.PulseEffect;
import com.github.gilbertotcc.lifx.models.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DoPulseEffectCommand
  implements Operation<DoPulseEffectInput, List<Result>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a pulse effect for the selected lights.
   *
   * @return {@link LifxResult.Success.Some} with the per-light results, {@link LifxResult.Success.None} if a
   *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
   *     failures
   */
  @Override
  public LifxResult<List<Result>> execute(DoPulseEffectInput input) {
    LightSelector lightSelector = input.lightSelector();
    PulseEffect pulseEffect = input.pulseEffect();

    log.info("Do pulse effect with {}. Settings: {}",
      lightSelector.identifier(),
      pulseEffect);
    return OperationCalls.executeCommand(lifxApi.pulseEffect(lightSelector, pulseEffect));
  }
}
