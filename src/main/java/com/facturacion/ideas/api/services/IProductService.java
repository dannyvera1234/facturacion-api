package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.dto.ProductResponseDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.TaxProduct;
import com.facturacion.ideas.api.entities.TaxValue;
import com.facturacion.ideas.api.enums.TypeTaxEnum;

public interface IProductService {

	ProductResponseDTO save(ProductDTO productDTO, Long idSubsidiary);

	ProductResponseDTO findById(Long ide);

	List<ProductResponseDTO> findAll(Long idSubsidiary);

	String deleteById(Long ide);

	ProductResponseDTO update(ProductDTO productDTO, Long ide);

	// ProductInformation

	List<ProductInformationDTO> findProductInforAll(Long idProducto);

	Boolean existsByCodePrivateAndSubsidiaryIde(String codePrivate, Long idSubsidiary);

	String deleteProductInfoById(Long idProducto, Long ide);

	String deleteProductInfoAllById(Long idProducto);

	ProductInformationDTO updateProductInfo(ProductInformationDTO productInformationDTO, Long ide);

	TaxProduct finTaxProduct(TypeTaxEnum typeTax, String ideTax, Product product);

}
