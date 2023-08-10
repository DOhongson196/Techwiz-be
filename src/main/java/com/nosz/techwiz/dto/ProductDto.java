package com.nosz.techwiz.dto;

import com.nosz.techwiz.entity.Enum.ProductStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;


@Data
public class ProductDto implements Serializable {
    private Long id;
    @NotEmpty(message = "Name is required")
    private String name;
    private Double price;
    private Boolean isFeatured;
    private String description;
    private ProductStatus status;
    private String image;
    private Long quantity;
    @Min(value = 0)
    @Max(value = 100)
    private Float discount;
    private Long categoryId;
    private CategoryDto category;

}
