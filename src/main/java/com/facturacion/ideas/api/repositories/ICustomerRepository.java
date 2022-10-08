package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long>{

}
