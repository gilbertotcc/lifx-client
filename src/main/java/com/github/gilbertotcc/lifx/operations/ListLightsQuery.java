package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.Light;
import com.github.gilbertotcc.lifx.models.LightSelector;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ListLightsQuery implements Operation<ListLightsInput, CommandOutput.SomeOutput<List<Light>>> {

  private final LifxApi lifxApi;

  /**
   * Synchronously retrieves the selected lights.
   *
   * @return an unmodifiable copy of the light list on the right, or a call exception on the left for HTTP or I/O
   *     failures, a successful response without a body, or a response list with a null element
   */
  @Override
  public Either<LifxCallException, CommandOutput.SomeOutput<List<Light>>> execute(ListLightsInput input) {
    LightSelector lightSelector = input.selector();

    log.info("List lights (selector: {})", lightSelector.identifier());
    var call = lifxApi.listLights(lightSelector);
    return new CallExecutor<>(call).executeExpectingBody()
      .flatMap(lights -> lights.stream().anyMatch(Objects::isNull)
        ? Either.left(LifxCallException.with(call, new IOException("Response list contains a null light")))
        : Either.right(new CommandOutput.SomeOutput<>(List.copyOf(lights))));
  }
}
