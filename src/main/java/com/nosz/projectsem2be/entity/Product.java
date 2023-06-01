package com.nosz.projectsem2be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Column(name = "view_count")
    private Long viewCount;

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

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "discount")
    private Float discount;


    @Column(name = "image")
    private String image;

    @PrePersist
    public void prePersist() {
        createDate = new Date();

        if(isFeatured == null) isFeatured = false;
        viewCount = 0L;
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = new Date();
    }
}