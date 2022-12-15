package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.*;
import com.facturacion.ideas.api.entities.DeatailsInvoiceProduct;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.ProductInformation;

public interface IProductMapper {

	Product mapperToEntity(ProductDTO productDTO);

	DeatailsInvoiceProduct mapperToEntity(DeatailsInvoiceProductDTO details);

	List<DeatailsInvoiceProduct> mapperToEntity(List<DeatailsInvoiceProductDTO> details);
	//ProductDTO mapperToDTO(Product product);

	ProductResponseDTO mapperToDTO(Product product);

	ProductEditDTO mapperToEditDTO(Product product);

	List<ProductResponseDTO> mapperToDTO(List<Product> products);

	void mapperPreUpdate(Product product, ProductDTO productDTO);

	ProductInformationDTO mapperProInformationToDTO(ProductInformation productInformation);

	List<ProductInformationDTO> mapperProInformationAToDTO(List<ProductInformation> productInformations);

	ProductInformation mapperProInformationToEntity(ProductInformationDTO ProductInformationDTO);

}
