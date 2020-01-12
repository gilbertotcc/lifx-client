package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.LightsState;
import com.github.gilbertotcc.lifx.models.LightsStatesDto;
import com.github.gilbertotcc.lifx.models.OperationResult;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
public class SetLightsStatesCommand
  implements Operation<SetLightsStatesInput, CommandOutput<List<OperationResult>>, LifxCallException> {

  private final LifxApi lifxApi;

  @Override
  public Either<LifxCallException, CommandOutput<List<OperationResult>>> execute(SetLightsStatesInput input) {
    log.info("Set lights states of {}", lightSelectorListOf(input));
    return new CallExecutor<>(lifxApi.setLightsStates(input)).execute()
      .map(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }

  private static String lightSelectorListOf(final LightsStatesDto lightsStatesDto) {
    return lightsStatesDto.getLightsStates().stream()
      .map(LightsState::getLightSelector)
      .map(LightSelector::identifier)
      .collect(joining(","));
  }
}
