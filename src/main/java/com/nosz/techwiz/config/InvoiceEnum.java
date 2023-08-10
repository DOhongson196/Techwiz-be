package com.nosz.techwiz.config;

import com.nosz.techwiz.entity.Enum.InvoiceStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceEnum implements Converter<String, InvoiceStatus> {
    @Override
    public InvoiceStatus convert(String value) {
        return InvoiceStatus.of(Integer.valueOf(value));
    }
}