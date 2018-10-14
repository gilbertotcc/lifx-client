package com.github.gilbertotcc.lifx.models.converter;

import java.io.IOException;

import com.github.gilbertotcc.lifx.models.LightsSelector;
import retrofit2.Converter;

public class SelectorConverter implements Converter<LightsSelector, String> {

    @Override
    public String convert(final LightsSelector lightsSelector) throws IOException {
        return lightsSelector.getIdentifier();
    }
}
