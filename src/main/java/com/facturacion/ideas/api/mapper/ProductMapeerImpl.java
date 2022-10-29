package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.dto.ProductResponseDTO;
import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.ProductInformation;
import com.facturacion.ideas.api.enums.TypeProductEnum;
import com.facturacion.ideas.api.util.FunctionUtil;

@Component
public class ProductMapeerImpl implements IProductMapper {

	@Override
	public Product mapperToEntity(ProductDTO productDTO) {

		Product product = new Product();

		product.setIde(productDTO.getIde());
		product.setCodePrivate(productDTO.getCodePrivate());
		product.setCodeAuxilar(productDTO.getCodeAuxilar());
		product.setName(productDTO.getName());
		product.setUnitValue(productDTO.getUnitValue());

		for (ProductInformationDTO productInformationDTO : productDTO.getProductInformationDTOs()) {

			product.addProductoInformation(mapperProInformationToEntity(productInformationDTO));

		}

		product.setTypeProductEnum(TypeProductEnum.getTypeProductEnum(productDTO.getTypeProductEnum()));
	
		// Si llega null, quiere decir que no selecciono algun tipo Impuesto
		product.setIva(productDTO.getIva());
		product.setIce(productDTO.getIce());
		product.setIrbpnr(productDTO.getIrbpnr());

		return product;
	}

	@Override
	public ProductResponseDTO mapperToDTO(Product product) {

		ProductResponseDTO productResponseDTO = new ProductResponseDTO();

		productResponseDTO.setIde(product.getIde());
		productResponseDTO.setCodePrivate(product.getCodePrivate());
		//productResponseDTO.setCodeAuxilar(product.getCodeAuxilar());
		productResponseDTO.setName(product.getName());
		//productResponseDTO.setDateCreate(FunctionUtil.convertDateToString(product.getDateCreate()));
		productResponseDTO.setUnitValue(product.getUnitValue());
		productResponseDTO.setTypeProductEnum(TypeProductEnum.getTypeProductEnum(product.getTypeProductEnum()).name());
		//productResponseDTO.setIva(product.getIva());
		//productResponseDTO.setIce(product.getIce());
		//productResponseDTO.setIrbpnr(product.getIrbpnr());
		// productResponseDTO.setSender(product.getSender().getSocialReason());

		return productResponseDTO;
	}

	@Override
	public void mapperPreUpdate(Product product, ProductDTO productDTO) {

		product.setName(productDTO.getName() == null ? product.getName() : productDTO.getName());
		product.setUnitValue(productDTO.getUnitValue());

		TypeProductEnum typeProductEnum = TypeProductEnum.getTypeProductEnum(productDTO.getTypeProductEnum());

		if (typeProductEnum != null)
			product.setTypeProductEnum(typeProductEnum);

		product.setCodeAuxilar(
				productDTO.getCodeAuxilar() == null ? product.getCodeAuxilar() : productDTO.getCodeAuxilar());
		product.setIva(productDTO.getIva() == null ? product.getIva() : productDTO.getIva());
		product.setIce(productDTO.getIce() == null ? product.getIce() : productDTO.getIce());
		product.setIrbpnr(productDTO.getIrbpnr() == null ? product.getIrbpnr() : productDTO.getIrbpnr());

	}

	@Override
	public ProductInformationDTO mapperProInformationToDTO(ProductInformation productInformation) {

		ProductInformationDTO productInformationDTO = new ProductInformationDTO();

		productInformationDTO.setIde(productInformation.getIde());
		productInformationDTO.setAttribute(productInformation.getAttribute());
		productInformationDTO.setValue(productInformation.getValue());
		return productInformationDTO;
	}

	@Override
	public ProductInformation mapperProInformationToEntity(ProductInformationDTO productInformationDTO) {

		ProductInformation productInformation = new ProductInformation();

		productInformation.setIde(productInformationDTO.getIde());
		productInformation.setAttribute(productInformationDTO.getAttribute());
		productInformation.setValue(productInformationDTO.getValue());
		return productInformation;
	}

	@Override
	public List<ProductInformationDTO> mapperProInformationAToDTO(List<ProductInformation> productInformations) {

		List<ProductInformationDTO> productInformationDTOs = new ArrayList<>();
		
		productInformationDTOs = productInformations.stream()
															.map(item -> mapperProInformationToDTO(item))
															.collect(Collectors.toList());

		return productInformationDTOs;
	}

}
