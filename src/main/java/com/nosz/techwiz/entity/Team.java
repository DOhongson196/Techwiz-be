package com.nosz.techwiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    @Column(name = "description")
    private String description;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "homeYardId")
    private Stadium homeYardId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}