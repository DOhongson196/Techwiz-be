package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainsIgnoreCase(String name);

    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);

    boolean existsByCategory_Id(Long id);

    boolean existsByManufacturer_Id(Long id);


}