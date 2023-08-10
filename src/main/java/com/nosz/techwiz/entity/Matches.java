package com.nosz.techwiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "matches")
public class Matches extends AbstractEntity {
    private String name;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "team_home_id")
    private Team teamHome;
    @ManyToOne
    @JoinColumn(name = "team_away_id")
    private Team teamAway;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "match_schedule_time")
    private Long matchScheduleTime;
    private Long totalTime;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}