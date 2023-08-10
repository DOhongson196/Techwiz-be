package com.nosz.techwiz.dto;

import com.nosz.techwiz.entity.Enum.ProductStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDtoBrief implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private Long viewCount;
    private Long volume;
    private Boolean isFeatured;
    private ProductStatus status;
    private String image;
    private Float discount;
    private String categoryName;
}
