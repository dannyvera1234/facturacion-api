package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface ISubsidiaryRepository extends JpaRepository<Subsidiary, Long> {

	
	Optional<Subsidiary> findByIdeAndSender(Long ide, Sender sender);
	
	
}
