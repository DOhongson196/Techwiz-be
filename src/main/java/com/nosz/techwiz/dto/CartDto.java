package com.nosz.techwiz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartDto implements Serializable {
    private Long Id;
    private Long userId;
    private Long productId;
    private Double subTotal;
}