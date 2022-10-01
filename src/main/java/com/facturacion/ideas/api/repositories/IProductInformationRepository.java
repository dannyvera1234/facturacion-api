package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.facturacion.ideas.api.entities.ProductInformation;

public interface IProductInformationRepository extends JpaRepository<ProductInformation, Long> {

	@Modifying
	@Query(value = "DELETE FROM INFO_ADICIONAL  where INF_FK_COD_PRO = ?1 AND INF_COD = ?2", nativeQuery = true)
	Integer deleteProductInformation(Long idProducto, Long idProductInfo);

	@Modifying
	@Query(value = "DELETE FROM INFO_ADICIONAL  where INF_FK_COD_PRO = ?1 ", nativeQuery = true)
	Integer deleteProductInformationAll(Long idProducto);


	@Query(value = "SELECT INF_COD, INF_ATR, INF_VAL FROM INFO_ADICIONAL WHERE  INF_FK_COD_PRO = ?1 AND INF_COD = ?2", nativeQuery = true)
	Optional<ProductInformation> findByIdProductoAndBy(Long idProducto, Long idProductInfo);
}
