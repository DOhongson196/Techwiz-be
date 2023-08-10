package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Long> {
}
