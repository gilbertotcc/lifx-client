package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.LifxResult;
import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ListLightsQuery implements Operation<ListLightsInput, List<Light>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously retrieves the selected lights.
   *
   * @return {@link LifxResult.Success.Some} with an unmodifiable copy of the light list, or
   *     {@link LifxResult.Failure} with a call exception for HTTP or I/O failures, a successful response without a
   *     body, or a response list with a null element. Never {@link LifxResult.Success.None}
   */
  @Override
  public LifxResult<List<Light>> execute(ListLightsInput input) {
    LightSelector lightSelector = input.selector();

    log.info("List lights (selector: {})", lightSelector.identifier());
    return OperationCalls.executeListQuery(lifxApi.listLights(lightSelector));
  }
}
