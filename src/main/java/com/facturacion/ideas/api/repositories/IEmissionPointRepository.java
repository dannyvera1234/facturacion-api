package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Subsidiary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEmissionPointRepository extends JpaRepository<EmissionPoint, Long> {

	EmissionPoint findByCodePointAndSubsidiary(String codePoint, Subsidiary subsidiary);

	Boolean existsByCodePointAndSubsidiaryIde(String codePoint, Long ide);

	List<EmissionPoint> findALlBySubsidiaryIde(Long ide);

	// , sub.ide, sub.code,sen.ide, sen.ruc, sen.typeEmission, sen.typeEnvironment
	@Query("select pte  from EmissionPoint pte join  fetch pte.subsidiary sub join  fetch sub.sender sen where pte.ide= :idEmision")
	Optional<EmissionPoint> fechtSubsidiaryToSender(@Param("idEmision") Long ideEmissionPoint);
}
