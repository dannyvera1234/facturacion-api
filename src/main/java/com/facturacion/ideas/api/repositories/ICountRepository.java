package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Count;

public interface ICountRepository extends JpaRepository<Count, Long> {

	
	Optional<Count> findByRuc(String ruc);
	
	
	
	
}
