package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}