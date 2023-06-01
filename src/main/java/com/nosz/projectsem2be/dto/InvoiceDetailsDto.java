package com.nosz.projectsem2be.dto;

import com.nosz.projectsem2be.entity.Invoice;
import com.nosz.projectsem2be.entity.Product;
import lombok.Data;

import java.io.Serializable;

@Data
public class InvoiceDetailsDto implements Serializable {
    private Long id;
    private String nameProduct;
    private Float discount;
    private Double price;
    private Double subTotal;
    private int quantity;
}
