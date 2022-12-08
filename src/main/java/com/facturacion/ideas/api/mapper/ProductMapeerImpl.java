package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.dto.ProductEditDTO;
import com.facturacion.ideas.api.dto.ProductResponseDTO;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypePorcentajeIvaEnum;
import com.facturacion.ideas.api.enums.TypeTaxEnum;
import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
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
        productResponseDTO.setIce(product.getIce());
        //productResponseDTO.setIrbpnr(product.getIrbpnr());
        // productResponseDTO.setSender(product.getSender().getSocialReason());

        return productResponseDTO;
    }

    @Override
    public ProductEditDTO mapperToEditDTO(Product product) {

        ProductEditDTO productEditDTO = new ProductEditDTO();
        productEditDTO.setIde(product.getIde());
        productEditDTO.setName(product.getName());
        productEditDTO.setCodePrivate(product.getCodePrivate());
        productEditDTO.setCodeAuxilar(product.getCodeAuxilar());
        productEditDTO.setUnitValue(product.getUnitValue());
        productEditDTO.setTypeProductEnum(product.getTypeProductEnum());

        List<TaxProduct> impuestos = product.getTaxProducts();

        for (TaxProduct taxProduct : impuestos) {

            TaxValue value = taxProduct.getTaxValue();
            Tax tax = value.getTax();

            // EL ide retorna el codigo del impuesto
            String codImpuesto = String.valueOf(tax.getIde());

            if (codImpuesto.equalsIgnoreCase(TypeTaxEnum.IVA.getCode())) {
                // Asigno el codigo del tipo de producto
                productEditDTO.setIva(value.getCode());
            }

            if (codImpuesto.equalsIgnoreCase(TypeTaxEnum.IRBPNR.getCode())) {
                // Asigno el codigo del tipo de producto
                productEditDTO.setIrbpnr(value.getCode());
            }

            if (codImpuesto.equalsIgnoreCase(TypeTaxEnum.ICE.getCode())) {
                // Asigno el codigo del tipo de producto
                productEditDTO.setIce(value.getCode());
            }
        }

        List<ProductInformation> produtDetails = product.getProductInformations();

        if (produtDetails.size() > 0) {

            List<ProductInformationDTO> listDetails = mapperProInformationAToDTO(produtDetails);
            productEditDTO.setProductInformationDTOs(listDetails);
        }

        return productEditDTO;
    }

    @Override
    public List<ProductResponseDTO> mapperToDTO(List<Product> products) {

        if (products != null && !products.isEmpty()) {

            return products.stream()
                    .map(this::mapperToDTO)
                    .collect(Collectors.toList());

        }
        return new ArrayList<>(0);
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


        // Solo si es IVA 12% decimos que si grava IVA
        boolean gravaIva = product.getIva().equalsIgnoreCase(TypePorcentajeIvaEnum.IVA_DOCE.getCode());
        product.setIva(gravaIva
                ? QuestionEnum.SI.name() : QuestionEnum.NO.name()); // Valor por defecto

        product.setIva(productDTO.getIva() == null ? "NO" : "SI");
        product.setIce(productDTO.getIce() == null ? "NO" : "SI");
        product.setIrbpnr(productDTO.getIrbpnr() == null ? "NO" : "SI");


        List<ProductInformationDTO> list =productDTO.getProductInformationDTOs();

        List<ProductInformation> listEntity = new ArrayList<>();

        for (ProductInformationDTO item: list){

            ProductInformation proInfo = mapperProInformationToEntity(item);
            listEntity.add(proInfo);
        }

        product.setProductInformations(listEntity);
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
