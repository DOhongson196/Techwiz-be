package com.nosz.techwiz.entity;

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
@Table(name = "player")
public class Player extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "national")
    private String national;

    @Column(name = "position")
    private String position;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "image")
    private String image;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "number")
    private Integer number;
}