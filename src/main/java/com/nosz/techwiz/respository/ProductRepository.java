package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.Enum.CategoryStatus;
import com.nosz.techwiz.entity.Enum.ProductStatus;
import com.nosz.techwiz.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);


    Page<Product> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<Product> findByStatusNotAndCategory_IdAndNameContainsIgnoreCaseAndPriceBetween(ProductStatus status, Long id, String name, Double priceStart, Double priceEnd, Pageable pageable);

    boolean existsByCategory_Id(Long id);


    List<Product> findByNameContainsAndStatusNotAndCategory_Status(String name, ProductStatus status, CategoryStatus status1);

}