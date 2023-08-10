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
@Table(name = "team")
public class Team extends AbstractEntity {
    @Column(name = "team_name")
    private String teamName;

    @Column(name = "country")
    private String country;

    @Column(name = "logo")
    private String logo;

}