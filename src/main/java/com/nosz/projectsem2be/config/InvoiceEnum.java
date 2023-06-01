package com.nosz.projectsem2be.config;

import com.nosz.projectsem2be.entity.InvoiceStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceEnum implements Converter<String, InvoiceStatus>{
    @Override
    public InvoiceStatus convert(String value) {
        return InvoiceStatus.of(Integer.valueOf(value));
    }
}
