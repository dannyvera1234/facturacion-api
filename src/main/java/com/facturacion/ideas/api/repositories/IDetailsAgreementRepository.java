package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.DetailsAggrement;

public interface IDetailsAgreementRepository extends JpaRepository<DetailsAggrement, Long> {

}
