package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByCedula(String cedula);
}
