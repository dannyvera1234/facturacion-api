package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductEditDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.dto.ProductResponseDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.TaxProduct;
import com.facturacion.ideas.api.entities.TaxValue;
import com.facturacion.ideas.api.enums.TypeTaxEnum;

public interface IProductService {

	ProductResponseDTO save(ProductDTO productDTO, boolean typeAction);

	ProductResponseDTO findById( Long idProduct);

	ProductEditDTO fetchTaxValueAndInfoDetailsById(Long idProduct);

	ProductResponseDTO findByCodePrivate(String codePrivate);

	List<ProductResponseDTO> findAll(Long idSubsidiary);

	List<ProductResponseDTO> findAllBySender();

	String deleteById(Long idProduct);

	ProductResponseDTO update(ProductDTO productDTO, Long idProduct);

	// ProductInformation

	List<ProductInformationDTO> findProductInforAll(Long idProducto);

	Boolean existsByCodePrivateAndSubsidiaryIde(String codePrivate, Long idSubsidiary);

	String deleteProductInfoById(Long idProducto, Long ide);

	String deleteProductInfoAllById(Long idProducto);

	ProductInformationDTO updateProductInfo(ProductInformationDTO productInformationDTO, Long ide);

	TaxProduct finTaxProduct(TypeTaxEnum typeTax, String ideTax, Product product);

	List<ProductResponseDTO> searchByCodeAndName(String filtro);
}
