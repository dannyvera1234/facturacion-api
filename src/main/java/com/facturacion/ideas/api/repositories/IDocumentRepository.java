package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Document;

public interface IDocumentRepository extends JpaRepository<Document,Long> {

}
