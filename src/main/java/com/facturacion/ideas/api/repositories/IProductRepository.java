package com.facturacion.ideas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface IProductRepository extends JpaRepository<Product, Long> {

	Boolean existsByCodePrivateAndSubsidiaryIde(String codePrivate, Long idSubsidiary);
	List<Product> findBySubsidiaryIde(Long subsidiary);

	List<Product> findByIdeIn(List<Long> ide);
}
