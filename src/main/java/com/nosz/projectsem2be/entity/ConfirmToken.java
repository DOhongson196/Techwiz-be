package com.nosz.projectsem2be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "confirm_token")
public class ConfirmToken extends AbstractEntity {
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "token")
    private String token;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    public ConfirmToken(String token, LocalDateTime createAt, LocalDateTime expireAt, User user) {
        this.createAt = createAt;
        this.token = token;
        this.expireAt = expireAt;
        this.user = user;
    }
}