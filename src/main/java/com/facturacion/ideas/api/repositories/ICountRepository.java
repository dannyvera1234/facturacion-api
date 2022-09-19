package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Count;

public interface ICountRepository extends JpaRepository<Count, Long> {

	
	Count findByRuc(String ruc);
}
