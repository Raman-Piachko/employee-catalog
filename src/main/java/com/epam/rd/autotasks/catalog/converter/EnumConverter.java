package com.epam.rd.autotasks.catalog.converter;

import com.epam.rd.autotasks.catalog.constant.SortEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnumConverter implements Converter<String, SortEnum> {
    @Override
    public SortEnum convert(String source) {
        try {
            return SortEnum.valueOf(source.toUpperCase());
        } catch (Exception E) {
            return null;
        }
    }
}