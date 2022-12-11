package com.facturacion.ideas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturacion.ideas.api.entities.Count;

public interface ICountRepository extends JpaRepository<Count, Long> {

	
	Optional<Count> findByRuc(String ruc);
	
	boolean existsByRuc(String ruc);
	
	
	@Query("select distinct c from Count c left join fetch c.detailsAggrement dt left join fetch dt.greement ag")
	List<Count> fetchByWithAgreement();

	/**
	 * Lista las cuentas excepto la cuenta del emisor actual, para evitar
	 * que sea eliminada por los admin
	 * @return
	 */
	@Query("select distinct c from Count c left join fetch c.detailsAggrement dt left join fetch dt.greement ag where not c.ruc =?1")
	List<Count> fetchByWithAgreement(String ruc);

	@Query("select  co.ide from Count  co where co.ruc =?1")
	Optional<Long> findIdByRuc(String ruc);

	@Query("select  co.ruc from Count  co where co.ide  =?1")
	Optional<String> findRucById(Long id);



} 
