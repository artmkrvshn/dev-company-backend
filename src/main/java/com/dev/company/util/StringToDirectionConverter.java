package com.dev.company.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;

public class StringToDirectionConverter implements Converter<String, Sort.Direction> {
    @Override
    public Sort.Direction convert(String source) {
        return Sort.Direction.fromString(source);
    }
}
