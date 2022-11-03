package com.facturacion.ideas.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> { 
	
	
	// Verifica si el emplado  a traves de su cedula esta registrado en la empresa o emisor
	boolean  existsByCedulaAndSenderIde(String cedula, Long ide);
	
	
	// Verifica si la cedula ya esta registrada en un establecimiento en particular
	boolean existsByCedulaAndSubsidiaryIde(String cedula, Long ide);
	
	// Eliminar un empleado de un emisor en particular
	@Modifying
	@Query("DELETE FROM Employee ep WHERE ep.sender.ide= :idSender and ep.ide= :idEmployee")
	int deleteBySender(@Param("idSender") Long idSender, @Param("idEmployee")  Long idEmployee);
	


	@Query("select  emp from Employee emp  where   emp.sender.ide = :param1 and  emp.subsidiary.ide = :param2 and  (emp.name like  :param3% or emp.cedula like :param3%)")
	List<Employee> findAllSubsidiary(@Param("param1") Long idSender, @Param("param2") Long idSub, @Param("param3") String filtro);
}
