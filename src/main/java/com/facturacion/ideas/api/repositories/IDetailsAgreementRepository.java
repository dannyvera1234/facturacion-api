package com.facturacion.ideas.api.repositories;

import org.hibernate.sql.Insert;
import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.DetailsAggrement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDetailsAgreementRepository extends JpaRepository<DetailsAggrement, Long> {


    /**
     * LIstas los planes contratados por una persona
     * @return
     */
    @Query("select  dt from DetailsAggrement dt inner join  fetch dt.greement where dt.count.ruc = :ruc")
    List<DetailsAggrement> listAllDetailAgreementsByRuc(@Param("ruc") String ruc);

    Boolean existsByGreementCodigo(Long codigo);

}
