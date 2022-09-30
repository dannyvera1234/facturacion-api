package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductDTO;

public interface IProductService {

	ProductDTO save(ProductDTO productDTO, Long idSubsidiary);

	ProductDTO findById(Long ide);

	List<ProductDTO> findAll(Long idSubsidiary);

	String deleteById(Long ide);

	ProductDTO update(ProductDTO productDTO, Long ide);

}
