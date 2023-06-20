package com.nosz.projectsem2be.respository;

import com.nosz.projectsem2be.entity.Invoice;
import com.nosz.projectsem2be.entity.InvoiceStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByUser_EmailContainsIgnoreCase(String email, Pageable pageable);

    @Query("SELECT i FROM Invoice i where  i.id =?1")
    Invoice findOrderById(Long Id);

    @Query("select i from Invoice i where upper(i.user.email) like upper(concat('%', ?1, '%')) and i.invoiceStatus in ?2")
    List<Invoice> findByUser_EmailContainsIgnoreCaseOrInvoiceStatus(String email,Collection<InvoiceStatus> invoiceStatus, Pageable pageable);

    List<Invoice> findByEmail(String email, Pageable pageable);




}