package com.nosz.techwiz.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private String nameProduct;
    private String image;
    private Float discount;
    private Double price;
    private Double subTotal;
    private int quantity;
}
