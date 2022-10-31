package com.facturacion.ideas.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

	Boolean existsByCodePrivateAndSubsidiaryIde(String codePrivate, Long idSubsidiary);
	List<Product> findBySubsidiaryIde(Long subsidiary);

	List<Product> findByIdeIn(List<Long> ide);

	@Query("select  pro from  Product  pro where   pro.subsidiary.ide = :param1  and (pro.codePrivate like %:param2% or pro.name like %:param2%)")
	List<Product> findBySubsidiaryAndName(@Param("param1") Long idSubsidiary, @Param("param2") String filtro );


}

