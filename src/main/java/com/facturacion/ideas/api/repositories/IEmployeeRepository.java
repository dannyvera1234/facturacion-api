package com.facturacion.ideas.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> { 
	
	
	// Verifica si el emplado  a traves de su cedula esta registrado en la empresa o emisor
	boolean  existsByCedulaAndSender(String cedula, Sender sender);
	
	
	// Verifica si la cedula ya esta registrada en un establecimiento en particular
	boolean existsByCedulaAndSubsidiary(String cedula, Subsidiary subsidiary);
	
	
	
	
}
