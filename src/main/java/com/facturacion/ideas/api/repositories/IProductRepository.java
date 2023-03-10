package com.facturacion.ideas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

    Boolean existsByCodePrivateAndSubsidiaryIde(String codePrivate, Long idSubsidiary);

    List<Product> findBySubsidiaryIde(Long subsidiary);

    List<Product> findByIdeIn(List<Long> ide);

    @Query("select  distinct pro from Product pro  join fetch  pro.taxProducts taxpro  join fetch  taxpro.taxValue taxvl  where  pro.ide in :ide")
    List<Product> fetchTaxValueTaxByIdeIn(@Param("ide") List<Long> ide);

    Optional<Product> findByCodePrivate(String codePrivate);

    @Query("select  pro from  Product  pro where   pro.subsidiary.ide = :param1  and (pro.codePrivate like :param2% or pro.name like :param2%)")
    List<Product> findBySubsidiaryAndName(@Param("param1") Long idSubsidiary, @Param("param2") String filtro);

    List<Product> findBySubsidiarySenderIde(Long idSender);

    /**
     * Consulta todos los datos de un producto, sus impuestos y la informacion adicional
     * @param ide
     * @return
     */
    @Query("select pro from Product pro left join pro.productInformations  join fetch  pro.taxProducts taxpro  join fetch  taxpro.taxValue tv join fetch  tv.tax where  pro.ide =:ide")
    Optional<Product> fetchTaxValueAndInfoDetailsById(@Param("ide") Long ide);

}

