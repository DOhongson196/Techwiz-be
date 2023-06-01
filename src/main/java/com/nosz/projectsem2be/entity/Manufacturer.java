package com.nosz.projectsem2be.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "manufacturer")
public class Manufacturer extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo", length = 80)
    private String logo;

}