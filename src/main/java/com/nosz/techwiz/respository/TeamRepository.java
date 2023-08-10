package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}