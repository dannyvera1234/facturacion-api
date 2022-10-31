package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.dto.ProductResponseDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.ProductInformation;

public interface IProductMapper {

	Product mapperToEntity(ProductDTO productDTO);

	//ProductDTO mapperToDTO(Product product);

	ProductResponseDTO mapperToDTO(Product product);

	List<ProductResponseDTO> mapperToDTO(List<Product> products);

	void mapperPreUpdate(Product product, ProductDTO productDTO);

	ProductInformationDTO mapperProInformationToDTO(ProductInformation productInformation);

	List<ProductInformationDTO> mapperProInformationAToDTO(List<ProductInformation> productInformations);

	ProductInformation mapperProInformationToEntity(ProductInformationDTO ProductInformationDTO);

}
