package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.News;
import com.nosz.techwiz.entity.TypeNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsByTitle(String name);

    List<News> findNewsByTypeNews(TypeNews typeNews);
}