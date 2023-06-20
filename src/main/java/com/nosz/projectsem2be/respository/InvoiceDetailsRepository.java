package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.InvoiceDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {
    boolean existsByProduct_Id(Long id);
    List<InvoiceDetails> findByInvoice_Id(Long id, Pageable pageable);





}