package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facturacion.ideas.api.entities.Sender;

public interface ISenderRepository extends JpaRepository<Sender, Long> {
	
	
	Optional<Sender> findByRuc(String ruc);
	
	@Query("select true from Sender  sd where sd.ruc =?1")
	Optional<Boolean> senderIsExist(String ruc);

}
