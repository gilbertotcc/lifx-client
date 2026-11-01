package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.OperationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
public class SetLightsStatesCommand
  implements Operation<SetLightsStatesInput, List<OperationResult>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously requests the state updates supplied for each selector.
   *
   * @return {@link LifxResult.Success.Some} with the per-operation results, {@link LifxResult.Success.None} if a
   *     successful response has no body, or {@link LifxResult.Failure} with a call exception for HTTP or I/O
   *     failures
   */
  @Override
  public LifxResult<List<OperationResult>> execute(SetLightsStatesInput input) {
    log.info("Set lights states of {}", lightSelectorListOf(input));
    return OperationCalls.executeCommand(lifxApi.setLightsStates(input));
  }

  private static String lightSelectorListOf(final LightsStatesDto lightsStatesDto) {
    return lightsStatesDto.getLightsStates().stream()
      .map(LightsState::getLightSelector)
      .map(LightSelector::identifier)
      .collect(joining(","));
  }
}
