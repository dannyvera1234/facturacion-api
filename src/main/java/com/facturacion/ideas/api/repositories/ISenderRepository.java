package com.facturacion.ideas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;

public interface ISenderRepository extends JpaRepository<Sender, Long> {
	
	
	Optional<Sender> findByRuc(String ruc);
	
	@Query("select true from Sender  sd where sd.ruc =?1")
	Optional<Boolean> senderIsExist(String ruc);
	Sender findByCount(Count count);
	
	@Query("select s from Sender s left join fetch s.count c where s.count.ide= :idCount")
	Optional<Sender> fetchByWithCount(@Param("idCount") Long idCount);

	boolean existsByRuc(String ruc);

	@Query("select  sd.socialReason from Sender  sd where sd.ruc =?1")
	Optional<String> findNameSenderByRuc(String ruc);

	@Query("select  sd.ide from Sender  sd where sd.ruc =?1")
	Optional<Long> findIdByRuc(String ruc);

	@Query("select se from Sender  se join fetch  se.subsidiarys where se.ruc=?1")
	Optional<Sender> fetchSubsidiaryAndPuntosEmisionEmailByRuc(String ruc);
}
