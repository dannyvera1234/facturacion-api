package com.facturacion.ideas.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> { 
	
	
	// Verifica si el emplado  a traves de su cedula esta registrado en la empresa o emisor
	boolean  existsByCedulaAndSender(String cedula, Sender sender);
	
	
	// Verifica si la cedula ya esta registrada en un establecimiento en particular
	boolean existsByCedulaAndSubsidiary(String cedula, Subsidiary subsidiary);
	
	// Eliminar un empleado de un emisor en particular
	@Modifying
	@Query("DELETE FROM Employee ep WHERE ep.sender.ide= :idSender and ep.ide= :idEmployee")
	int deleteBySender(@Param("idSender") Long idSender, @Param("idEmployee")  Long idEmployee);
	
	
}
