package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.TaxProduct;
import com.facturacion.ideas.api.entities.TaxValue;
import com.facturacion.ideas.api.enums.TypeTaxEnum;

public interface IProductService {

	ProductDTO save(ProductDTO productDTO, Long idSubsidiary);

	ProductDTO findById(Long ide);

	List<ProductDTO> findAll(Long idSubsidiary);

	String deleteById(Long ide);

	ProductDTO update(ProductDTO productDTO, Long ide);

	// ProductInformation

	ProductInformationDTO findProductInforById(Long idProducto, Long id);

	List<ProductInformationDTO> findProductInforAll(Long idProducto);

	String deleteProductInfoById(Long idProducto, Long ide);

	String deleteProductInfoAllById(Long idProducto);

	ProductInformationDTO updateProductInfo(ProductInformationDTO productInformationDTO, Long ide);

	TaxProduct finTaxProduct(TypeTaxEnum typeTax, String ideTax, Product product);

}
