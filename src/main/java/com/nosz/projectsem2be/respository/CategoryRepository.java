package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Category;
import com.nosz.projectsem2be.entity.CategoryStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    List<Category> findByStatus(CategoryStatus status);

}