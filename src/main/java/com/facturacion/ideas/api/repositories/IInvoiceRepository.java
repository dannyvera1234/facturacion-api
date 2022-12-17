package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Invoice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {


    @Query("select inv from Invoice inv join fetch  inv.valueInvoice left  join inv.person where inv.rucSender=?1 order by inv.ide desc ")
    List<Invoice> findAllDocumentsBySender(String rucSender);

    @Query("select inv from Invoice inv join fetch  inv.valueInvoice where inv.ide=?1")
    Optional<Invoice> findInvoiceFetchValues(Long idInvoice);
}
