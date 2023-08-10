package com.nosz.techwiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "invoice_details")
public class InvoiceDetails extends AbstractEntity {
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Float discount;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Transient
    public double getSubtotal(){
        if(this.getDiscount() != 0){
            return Math.round((this.getPrice()*((100-this.getDiscount())/100)) * quantity);
        }else{
            return this.getPrice()*quantity;
        }
    }
}