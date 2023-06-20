package com.nosz.projectsem2be.entity;

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

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Transient
    public double getSubtotal(){
        if(this.product.getDiscount() != 0){
            return Math.round((this.product.getPrice()*((100-this.product.getDiscount())/100)) * quantity);
        }else{
            return this.product.getPrice()*quantity;
        }
    }
}