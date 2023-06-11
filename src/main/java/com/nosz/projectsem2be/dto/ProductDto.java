package com.nosz.projectsem2be.dto;



import com.nosz.projectsem2be.entity.ProductStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDto implements Serializable {
    private Long id;
    @NotEmpty(message = "Name is required")
    private String name;
    private Double price;
    @Min(value = 0)
    private Long viewCount;
    @Min(value = 0)
    private Long volume;
    private Boolean isFeatured;
    private String description;
    private ProductStatus status;

    private String image;
    @Min(value = 0)
    @Max(value = 100)
    private Float discount;

    private Long categoryId;

    private Long manufacturerId;
    private CategoryDto category;
    private ManufacturerDto manufacturer;


}
