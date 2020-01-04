package com.github.gilbertotcc.lifx.impl.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.impl.LifxCallExecutor;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ListLightsOperation implements Operation<LightSelector, OperationResult<List<Light>>> {

  private final LifxApi lifxApi;

  @Override
  public OperationResult<List<Light>> execute(LightSelector lightsSelector) {
    log.info("List lights (selector: {})", lightsSelector.identifier());
    return Try.of(LifxCallExecutor.of(lifxApi.listLights(lightsSelector))::getResponse)
      .map(OperationResult::success)
      .getOrElseGet(OperationResult::failure);
  }
}
