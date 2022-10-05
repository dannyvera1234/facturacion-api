package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface IEmissionPointRepository extends JpaRepository<EmissionPoint, Long> {

	EmissionPoint findByCodePointAndSubsidiary(String codePoint, Subsidiary subsidiary);
}
