package com.nosz.techwiz.dto.filter;

import lombok.Data;

@Data
public abstract class BaseFilter {
    protected int pageIndex;
    protected int pageSize;
}
