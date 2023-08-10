package com.nosz.techwiz.dto.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class StadiumFilter extends BaseFilter {
    private String nameLike;
    private String addressLike;
}
