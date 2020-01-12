package com.github.gilbertotcc.lifx.operations;

import com.github.gilbertotcc.lifx.api.LifxApi;
import com.github.gilbertotcc.lifx.exception.LifxCallException;
import com.github.gilbertotcc.lifx.impl.CallExecutor;
import com.github.gilbertotcc.lifx.models.BreatheEffect;
import com.github.gilbertotcc.lifx.models.LightSelector;
import com.github.gilbertotcc.lifx.models.Result;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DoBreatheEffectCommand
  implements Operation<DoBreatheEffectInput, CommandOutput<List<Result>>, LifxCallException> {

  private final LifxApi lifxApi;

  @Override
  public Either<LifxCallException, CommandOutput<List<Result>>> execute(DoBreatheEffectInput input) {
    LightSelector lightSelector = input.getLightSelector();
    BreatheEffect breatheEffect = input.getBreatheEffect();

    log.info("Do breathe effect with {}. Settings: {}",
      lightSelector.identifier(), ReflectionToStringBuilder.toString(breatheEffect, ToStringStyle.JSON_STYLE));
    return new CallExecutor<>(lifxApi.breatheEffect(lightSelector, breatheEffect))
      .execute(response -> {
        var results = response.getResults();
        return new CommandOutput<>(results);
      });
  }
}
