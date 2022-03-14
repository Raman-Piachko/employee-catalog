package com.epam.rd.autotasks.catalog.converters;

import com.epam.rd.autotasks.catalog.constant.SortEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class SortConverter implements Converter<String, SortEnum> {
    @Override
    public SortEnum convert(String sort) {
        return SortEnum.valueOf(sort.toUpperCase(Locale.ROOT));
    }
}