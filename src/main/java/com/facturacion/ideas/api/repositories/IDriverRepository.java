package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Driver;

public interface IDriverRepository extends JpaRepository<Driver, Long> {

}
