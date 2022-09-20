package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Login;

public interface ISenderService {

	Count saveCount(Count count);
	
	Optional<Count> findCountByRuc(String ruc);
	
	Optional<Count> findCountById(Long id);
	
	List<Count> findCountAll();
	
	void deleteCountById(Long id);
	
	Login saveLoginIn(Login login) ;
	
	Count updateCount(Count count);
}
