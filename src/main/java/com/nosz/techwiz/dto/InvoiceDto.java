package com.nosz.techwiz.dto;


import com.nosz.techwiz.entity.Enum.InvoicePayment;
import com.nosz.techwiz.entity.Enum.InvoiceStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
@Data
public class InvoiceDto implements Serializable {
    private Long id;
    @NotEmpty(message = "Name is required")
    private String customerName;
    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Phone is required")
    private String phone;
    @Email
    private String email;
    @NotNull(message = "Payment is required")
    private InvoicePayment invoicePayment;
    private InvoiceStatus invoiceStatus;
    private Long userId;
    private String accountName;
}