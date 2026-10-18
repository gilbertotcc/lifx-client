package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.LightSelector;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ListLightsQuery implements Operation<ListLightsInput, ListLightsOutput, LifxCallException> {

  private final LifxApi lifxApi;

  /**
   * Synchronously retrieves the selected lights.
   *
   * @return the light list on the right, or a call exception on the left for HTTP or I/O failures
   * @throws NullPointerException if a successful response has no body or its list contains a null element
   */
  @Override
  public Either<LifxCallException, ListLightsOutput> execute(ListLightsInput input) {
    LightSelector lightSelector = input.selector();

    log.info("List lights (selector: {})", lightSelector.identifier());
    return new CallExecutor<>(lifxApi.listLights(lightSelector)).execute()
      .map(ListLightsOutput::new);
  }
}
