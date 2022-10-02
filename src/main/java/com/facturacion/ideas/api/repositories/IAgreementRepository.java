package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.enums.TypeAgreementEnum;

public interface IAgreementRepository extends JpaRepository<Agreement, Long>{

	
	 Optional<Agreement> findByTypeAgreement(TypeAgreementEnum typeAgreementEnum);
}
