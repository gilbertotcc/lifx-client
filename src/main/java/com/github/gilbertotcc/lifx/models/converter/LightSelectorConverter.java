package com.github.gilbertotcc.lifx.models.converter;

import com.github.gilbertotcc.lifx.models.LightSelector;
import retrofit2.Converter;

public class LightSelectorConverter implements Converter<LightSelector, String> {

  @Override
  public String convert(final LightSelector lightSelector) {
    return lightSelector.identifier();
  }
}
