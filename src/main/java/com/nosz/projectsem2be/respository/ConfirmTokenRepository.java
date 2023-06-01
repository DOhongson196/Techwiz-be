package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {
    Optional<ConfirmToken> findByToken(String token);

    ConfirmToken findByUser_Email(String email);



}