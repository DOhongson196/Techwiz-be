package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.Category;
import com.nosz.techwiz.entity.Enum.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    List<Category> findByStatus(CategoryStatus status);
}