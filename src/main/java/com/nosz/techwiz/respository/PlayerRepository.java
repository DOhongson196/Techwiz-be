package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}