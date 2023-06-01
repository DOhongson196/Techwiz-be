package com.nosz.projectsem2be.dto;



import com.nosz.projectsem2be.entity.ProductStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDtoBrief implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private Long viewCount;
    private Boolean isFeatured;
    private ProductStatus status;
    private String image;
    private Float discount;
    private String categoryName;
    private String manufacturerName;


}
