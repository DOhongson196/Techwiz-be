package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.CategoryStatus;
import com.nosz.projectsem2be.entity.Product;
import com.nosz.projectsem2be.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);


    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);

    boolean existsByCategory_Id(Long id);

    boolean existsByManufacturer_Id(Long id);

    List<Product> findByNameContainsAndStatusNotAndCategory_Status(String name, ProductStatus status, CategoryStatus status1);

    @Query(value = "SELECT * FROM Product p order by p.view_count DESC Limit 10",nativeQuery = true)
    @Modifying
    List<Product> selectTop10View();

    @Query(value = "SELECT * FROM Product p order by p.volume DESC Limit 10",nativeQuery = true)
    @Modifying
    List<Product> selectTop10Buy();

    @Query(value = "SELECT * FROM Product p where p.is_featured = 1 order by p.view_count DESC Limit 10",nativeQuery = true)
    @Modifying
    List<Product> selectTop10Featured();
}