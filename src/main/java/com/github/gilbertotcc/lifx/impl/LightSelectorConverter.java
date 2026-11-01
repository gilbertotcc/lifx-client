package com.github.gilbertotcc.lifx.impl;

import com.github.gilbertotcc.lifx.models.LightSelector;
import retrofit2.Converter;

final class LightSelectorConverter implements Converter<LightSelector, String> {

  @Override
  public String convert(final LightSelector lightSelector) {
    return lightSelector.identifier();
  }
}
