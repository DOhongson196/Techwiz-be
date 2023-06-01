package com.nosz.projectsem2be.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageDto implements Serializable {
    private String filename;

    private String url;
}
