package com.github.gilbertotcc.lifx.models.converter;

import javax.annotation.ParametersAreNonnullByDefault;

import com.github.gilbertotcc.lifx.models.LightsSelector;
import retrofit2.Converter;

public class LightsSelectorConverter implements Converter<LightsSelector, String> {

  @Override
  @ParametersAreNonnullByDefault
  public String convert(final LightsSelector lightsSelector) {
    return lightsSelector.getIdentifier();
  }
}
