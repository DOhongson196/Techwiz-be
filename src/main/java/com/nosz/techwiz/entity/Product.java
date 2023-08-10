package com.nosz.techwiz.entity;

import com.nosz.techwiz.entity.Enum.ProductStatus;
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
@Table(name = "product")
public class Product extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;


    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "description", length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "discount")
    private Float discount;


    @Column(name = "image")
    private String image;

    @Column(name = "quantity")
    private Long quantity;

    @PrePersist
    public void prePersist() {
        createDate = new Date();

        if(isFeatured == null) isFeatured = false;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }
}