package com.nosz.techwiz.respository;

import com.nosz.techwiz.entity.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Long> {
    boolean existsByProduct_Id(Long id);
    Page<InvoiceDetails> findByInvoice_Id(Long id, Pageable pageable);
}
