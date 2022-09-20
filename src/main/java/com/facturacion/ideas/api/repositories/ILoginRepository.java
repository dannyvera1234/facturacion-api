package com.facturacion.ideas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Login;

public interface ILoginRepository extends JpaRepository<Login, Long> {

}
