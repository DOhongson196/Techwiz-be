package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchesRepository extends JpaRepository<Matches, Long> {
}