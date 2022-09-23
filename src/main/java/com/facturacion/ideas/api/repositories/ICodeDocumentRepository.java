package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.CodeDocument;

public interface ICodeDocumentRepository extends JpaRepository<CodeDocument, Long> {

	@Query("SELECT MAX(c.numSubsidiary) FROM CodeDocument c  where c.codeCount = :codeCount GROUP BY c.codeCount")
	Optional<Integer> findNumberMaxSender(@Param("codeCount") Long codeCount);

	@Modifying
	@Query("DELETE FROM CodeDocument c WHERE c.codeCount = :codeCount")
	void deleteByCodeCount( @Param("codeCount") Long codeCount);

}
