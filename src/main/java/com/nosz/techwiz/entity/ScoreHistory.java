package com.nosz.techwiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "score_history")
public class ScoreHistory extends AbstractEntity {
    private String soccerPlayer;
    private Long time;
}
