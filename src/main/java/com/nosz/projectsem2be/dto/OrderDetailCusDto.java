package com.nosz.projectsem2be.dto;

import com.nosz.projectsem2be.entity.InvoiceStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailCusDto implements Serializable {
    private String nameProduct;
    private Float discount;
    private Double price;
    private Double subTotal;
    private int quantity;

    private InvoiceStatus invoiceStatus;
}
