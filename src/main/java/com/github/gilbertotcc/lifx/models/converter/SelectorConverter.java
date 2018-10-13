package com.github.gilbertotcc.lifx.models.converter;

import java.io.IOException;

import com.github.gilbertotcc.lifx.models.Selector;
import retrofit2.Converter;

public class SelectorConverter implements Converter<Selector, String> {

    @Override
    public String convert(final Selector selector) throws IOException {
        return selector.getIdentifier();
    }
}
