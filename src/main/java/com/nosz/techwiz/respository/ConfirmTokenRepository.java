package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {
    Optional<ConfirmToken> findByToken(String token);

    ConfirmToken findByUser_Email(String email);

    ConfirmToken findByUser_EmailAndToken(String email, String token);
}