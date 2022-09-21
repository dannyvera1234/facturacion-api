package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Agreement;

public interface IAgreementRepository extends JpaRepository<Agreement, String>{

}
