package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Page<Manufacturer> findByNameContainsIgnoreCase(String name, Pageable pageable);

    List<Manufacturer> findByNameContainsIgnoreCase(String name);

    boolean existsByName(String name);




}