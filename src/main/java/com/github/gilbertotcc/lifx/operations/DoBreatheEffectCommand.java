package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DoBreatheEffectCommand
  implements Operation<DoBreatheEffectInput, List<Result>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests a breathe effect for the selected lights.
   *
   * @return {@link LifxResult.Success.Some} with the per-light results, {@link LifxResult.Success.None} if a
   *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
   *     failures
   */
  @Override
  public LifxResult<List<Result>> execute(DoBreatheEffectInput input) {
    LightSelector lightSelector = input.lightSelector();
    BreatheEffect breatheEffect = input.breatheEffect();

    log.info("Do breathe effect with {}. Settings: {}",
      lightSelector.identifier(), breatheEffect);
    return OperationCalls.executeCommand(lifxApi.breatheEffect(lightSelector, breatheEffect));
  }
}
