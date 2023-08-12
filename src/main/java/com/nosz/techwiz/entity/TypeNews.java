package com.nosz.techwiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "type_news")
public class TypeNews extends  AbstractEntity {
    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;
}
