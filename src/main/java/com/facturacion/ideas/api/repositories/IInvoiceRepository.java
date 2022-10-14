package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Invoice;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {

}
