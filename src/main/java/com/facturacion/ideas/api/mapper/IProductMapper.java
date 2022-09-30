package com.facturacion.ideas.api.mapper;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.entities.Product;

public interface IProductMapper {

	Product mapperToEntity(ProductDTO productDTO);

	ProductDTO mapperToDTO(Product product);
	
	
	void mapperPreUpdate(Product product, ProductDTO productDTO);
}
