package com.nosz.techwiz.entity;

import com.nosz.techwiz.entity.Enum.InvoicePayment;
import com.nosz.techwiz.entity.Enum.InvoiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice extends AbstractEntity {
    @Column(name = "customer_name", length = 100)
    private String customerName;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "phone")
    private String phone;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Enumerated
    @Column(name = "invoice_status")
    private InvoiceStatus invoiceStatus;

    @Column(name = "email")
    private String email;

    @Enumerated
    @Column(name = "invoice_payment")
    private InvoicePayment invoicePayment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @PrePersist
    public void prePersist(){
        createDate = new Date();
    }
}