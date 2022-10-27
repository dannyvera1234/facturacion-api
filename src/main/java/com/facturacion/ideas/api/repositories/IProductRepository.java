package com.facturacion.ideas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface IProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Verifica la existencia de un producto dentro de un establecimiento
	 * @param codePrivate : codigo privado producto
	 * @param subsidiary : id establecimiento
	 * @return
	 */
	@Query("select true from Product pr where pr.codePrivate= :codePrivate and pr.subsidiary.ide= :subsidiary")
	Optional<Boolean>  existProductoBySubsidiary(@Param("codePrivate") String codePrivate, @Param("subsidiary") Long subsidiary);

	List<Product> findBySubsidiary(Subsidiary subsidiary);

	List<Product> findByIdeIn(List<Long> ide);

}
