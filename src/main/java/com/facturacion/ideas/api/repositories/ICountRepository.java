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

	@Query("select  co.ide from Count  co where co.ruc =?1")
	Optional<Long> findIdByRuc(String ruc);



} 
