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
@Table(name = "matches")
public class Matches extends AbstractEntity {
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "team_home_id")
    private Team teamHome;

    @ManyToOne
    @JoinColumn(name = "team_away_id")
    private Team teamAway;

    @Temporal(TemporalType.DATE)
    @Column(name = "match_day")
    private Date matchDay;

    @Temporal(TemporalType.TIME)
    @Column(name = "match_time")
    private Date matchTime;
}