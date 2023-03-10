package com.facturacion.ideas.api.controllers;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductEditDTO;
import com.facturacion.ideas.api.dto.ProductResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IProductOperation;
import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IProductService;
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class ProductRestController implements IProductOperation {

    private static final Logger LOGGER = LogManager.getLogger(ProductRestController.class);

    @Autowired
    private IProductService productService;

    @Override
    public ResponseEntity<ProductResponseDTO> save(ProductDTO productDTO) {

        LOGGER.info("Producto guardar: " + productDTO.getProductInformationDTOs().size());
        try {

            ProductResponseDTO productDTOSaved = productService.save(productDTO, true);

            return new ResponseEntity<>(productDTOSaved, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ProductResponseDTO> findById(Long id) {

        ProductResponseDTO productDTOSaved = productService.findById(id);

        return ResponseEntity.ok(productDTOSaved);
    }

    @Override
    public ResponseEntity<ProductEditDTO> fetchTaxValueAndInfoDetailsById(Long id) {

        try {
            ProductEditDTO productEditDTO = productService
                    .fetchTaxValueAndInfoDetailsById(id);
            return ResponseEntity.ok(productEditDTO);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ProductResponseDTO> findByCodePrivate(String codigo) {


        try {
            ProductResponseDTO productfind = productService.findByCodePrivate(codigo);
            return ResponseEntity.ok(productfind);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProductResponseDTO>> findAllByIdSubsidiary(Long id) {

        try {
            List<ProductResponseDTO> productDTOS = productService.findAll(id);

            return ResponseEntity.ok(productDTOS);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        LOGGER.info("Id producto eliminar: " + id);

        try {

            String smsResponse = productService.deleteById(id);
            return new ResponseEntity<>(smsResponse, HttpStatus.NO_CONTENT);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<ProductResponseDTO> update(ProductDTO productDTO, Long id) {
        LOGGER.info("Producto actualizar: " + productDTO.getProductInformationDTOs().size());
        try {
            ProductResponseDTO productDTOSaved = productService.save(productDTO, false);
            return new ResponseEntity<>(productDTOSaved, HttpStatus.OK);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProductInformationDTO>> findProducInformation(Long id) {

        try {

            List<ProductInformationDTO> productInformationDTOs = productService.findProductInforAll(id);

            return ResponseEntity.ok(productInformationDTOs);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());

        }
    }

    @Override
    public ResponseEntity<String> deleteProductInformation(Long id, Long idDetails) {

        try {

            String response = productService.deleteProductInfoById(id, idDetails);

            return new ResponseEntity<String>(response, HttpStatus.NO_CONTENT);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteProductInformationAll(Long id) {
        try {

            String response = productService.deleteProductInfoAllById(id);

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ProductInformationDTO> updateProInformation(ProductInformationDTO productInformationDTO,
                                                                      Long id) {

        try {

            ProductInformationDTO productInformationDTOUpdate = productService.updateProductInfo(productInformationDTO,
                    id);

            return ResponseEntity.ok(productInformationDTOUpdate);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProductResponseDTO>> searchByCodeAndName(String filtro) {
        try {
            List<ProductResponseDTO> productDTOS = productService.searchByCodeAndName(filtro);

            return ResponseEntity.ok(productDTOS);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProductResponseDTO>> findAllBySender() {
        try {
            List<ProductResponseDTO> productDTOS = productService.findAllBySender();
            return ResponseEntity.ok(productDTOS);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }


}
