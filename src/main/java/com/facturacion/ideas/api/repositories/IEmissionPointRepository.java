package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.EmissionPoint;

public interface IEmissionPointRepository extends JpaRepository<EmissionPoint, Long> {

}
