package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ListLightsQuery implements Operation<ListLightsInput, CommandOutput.SomeOutput<List<Light>>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously retrieves the selected lights.
   *
   * @return the light list on the right, or a call exception on the left for HTTP or I/O failures or a successful
   *     response without a body
   */
  @Override
  public Either<LifxCallException, CommandOutput.SomeOutput<List<Light>>> execute(ListLightsInput input) {
    LightSelector lightSelector = input.selector();

    log.info("List lights (selector: {})", lightSelector.identifier());
    return new CallExecutor<>(lifxApi.listLights(lightSelector)).executeExpectingBody()
      .map(CommandOutput.SomeOutput::new);
  }
}
