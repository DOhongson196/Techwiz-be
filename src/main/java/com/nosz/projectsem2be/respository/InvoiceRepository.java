package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Invoice;
import com.nosz.projectsem2be.entity.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    @Query("SELECT i FROM Invoice i where  i.id =?1")
    Invoice findOrderById(Long Id);

    Page<Invoice> findByUser_EmailContainsIgnoreCaseAndInvoiceStatusIn(String email, Collection<InvoiceStatus> invoiceStatuses, Pageable pageable);

    Page<Invoice> findByUser_Email(String email,Pageable pageable);




}