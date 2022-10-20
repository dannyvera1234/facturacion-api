package com.facturacion.ideas.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.TaxValue;


public interface ITaxValueRepository extends JpaRepository<TaxValue, Long> {

	
	
	// select * from impuesto_valor where codigo_impuesto=2and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A') and FECHA_INICIO <= '" + fechaEmisionString + "' and (FECHA_FIN >= '" + fechaEmisionString + "' or FECHA_FIN IS NULL) order by codigo_adm
	 
	
	/**
	 * Filtra los impuestos tipo iva=2, además que el tipo de impuesto sea igual a 'I' ó 'A',
	 * además que la fecha de inicio sea menor igual a la actuál,
	 * además que la fecha final sea mayor igual a la actual o sea nula.  
	 * @param fechaDeEmison : fecha actúal
	 * @return
	 */
	
	@Query("SELECT tv FROM TaxValue tv WHERE tv.tax.ide= 2 AND (tv.typeTax='I'  OR tv.typeTax='A') AND tv.srtartDate <= :fechaDeEmision AND (tv.endDate >= :fechaDeEmision OR tv.endDate IS NULL)")
	List<TaxValue> findAllIVA(@Param("fechaDeEmision") String fechaDeEmison);
	
	
	
	// select * from impuesto_valor where codigo_impuesto=3and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A' )"
	
	/**
	 * Filtra los impuesto tipo ice = 3
	 * @return
	 */
	@Query("SELECT tv FROM TaxValue tv WHERE tv.tax.ide= 3 AND (tv.typeTax='I'  OR tv.typeTax='A')")
	List<TaxValue> findAllICE();
	
	
	// select * from impuesto_valor where codigo_impuesto=5 and TIPO_IMPUESTO='B'
	@Query("SELECT tv FROM TaxValue tv WHERE tv.tax.ide= 5 AND tv.typeTax='B'")
	List<TaxValue> findAllIRBPNR();
	
	
}
