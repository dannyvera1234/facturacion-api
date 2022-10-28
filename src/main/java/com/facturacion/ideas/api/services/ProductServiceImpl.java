package com.facturacion.ideas.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.admin.AdminProduct;
import com.facturacion.ideas.api.enums.TypeTaxEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.ProductInformation;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.entities.TaxProduct;
import com.facturacion.ideas.api.entities.TaxValue;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IProductMapper;
import com.facturacion.ideas.api.repositories.IProductInformationRepository;
import com.facturacion.ideas.api.repositories.IProductRepository;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;
import com.facturacion.ideas.api.repositories.ITaxValueRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISubsidiaryRepository subsidiaryRepository;

    @Autowired
    private ITaxValueRepository taxValueRepository;

    @Autowired
    private IProductMapper productMapper;

    @Autowired
    private IProductInformationRepository productInformationRepository;

    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO, Long idSubsidiary) {

        Subsidiary subsidiary = findSubsidiaryByIdPrivate(idSubsidiary);

        isExistProductBySubsidiary(productDTO.getCodePrivate(), subsidiary.getIde());

        AdminProduct.numeroMaximoInfoAdicionalProduct(productDTO.getProductInformationDTOs());

        try {
            Product product = productMapper.mapperToEntity(productDTO);

            // Lista impuesto
            List<TaxProduct> taxValues = new ArrayList<>();

            // IVA
            String codeImpuesto = product.getIva(); // codigo impuesto IVA
            AdminProduct.asignarSiNoImpuesto(TypeTaxEnum.IVA, product);
            if (codeImpuesto != null)
                taxValues.add(finTaxProduct(TypeTaxEnum.IVA, codeImpuesto, product));

            // ICE
            codeImpuesto = product.getIce(); // codigo impuesto ICE
            AdminProduct.asignarSiNoImpuesto(TypeTaxEnum.ICE, product);
            if (codeImpuesto != null)
                taxValues.add(finTaxProduct(TypeTaxEnum.ICE, codeImpuesto, product));

            // IRBPNR
            codeImpuesto = product.getIrbpnr(); // codigo impuesto IRBPNR
            AdminProduct.asignarSiNoImpuesto(TypeTaxEnum.IRBPNR, product);
            if (codeImpuesto != null)
                taxValues.add(finTaxProduct(TypeTaxEnum.IRBPNR, codeImpuesto, product));


            product.setSubsidiary(subsidiary);
            // Asignar impuestos
            product.setTaxProducts(taxValues);

            return productMapper.mapperToDTO(productRepository.save(product));

        } catch (DataAccessException e) {

            LOGGER.info("Error guardar producto", e);
            throw new NotDataAccessException("Error guardar producto: " + e.getMessage());
        }
    }

    @Override
    public ProductDTO findById(Long ide) {

        Product product = findByIdPrivate(ide);
        return productMapper.mapperToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(Long idSubsidiary) {

        Subsidiary subsidiary = findSubsidiaryByIdPrivate(idSubsidiary);

        try {

            List<Product> products = productRepository.findBySubsidiary(subsidiary);

            return products.stream().map(item -> productMapper.mapperToDTO(item))
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {

            LOGGER.error("Error listar productos", e);
            throw new NotDataAccessException("Error listar productos: " + e.getMessage());

        }

    }

    @Override
    @Transactional
    public String deleteById(Long ide) {

        Product product = findByIdPrivate(ide);

        try {

            productRepository.deleteById(product.getIde());

            return "Producto eliminado correctamente";
        } catch (DataAccessException e) {

            LOGGER.error("Error eliminar producto", e);
            throw new NotDataAccessException("Error eliminar producto: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO, Long ide) {

        Product product = findByIdPrivate(ide);

        productMapper.mapperPreUpdate(product, productDTO);

        try {

            return productMapper.mapperToDTO(productRepository.save(product));

        } catch (DataAccessException e) {

            LOGGER.error("Error actualizar producto", e);
            throw new NotDataAccessException("Error actualizar producto: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public Subsidiary findSubsidiaryByIdPrivate(Long ide) {

        try {

            return subsidiaryRepository.findById(ide).orElseThrow(
                    () -> new NotFoundException("Id: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

        } catch (DataAccessException e) {

            LOGGER.error("Error buscar establecimiento", e);
            throw new NotDataAccessException("Error buscar establecimiento: " + e.getMessage());
        }

    }

    /**
     * Verifica si un producto existe en un establecimiento
     */
    @Transactional(readOnly = true)
    public void isExistProductBySubsidiary(String codePrivateProd, Long idSubsidiary) {

        try {
            if (!productRepository.existProductoBySubsidiary(codePrivateProd, idSubsidiary).isEmpty())
                throw new DuplicatedResourceException("codigo Producto: " + codePrivateProd
                        + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

        } catch (DataAccessException e) {

            LOGGER.error("Error  verificar existencia producto", e);
            throw new NotDataAccessException("Error verificar existencia producto: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public Product findByIdPrivate(Long ide) {

        try {

            return productRepository.findById(ide).orElseThrow(
                    () -> new NotFoundException("Id: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

        } catch (DataAccessException e) {

            LOGGER.error("Error buscar producto", e);
            throw new NotDataAccessException("Error buscar producto: " + e.getMessage());
        }

    }

    @Override
    public ProductInformationDTO findProductInforById(Long idProducto, Long ide) {

        try {

            ProductInformation productInformation = productInformationRepository.findByIdProductoAndBy(idProducto, ide)
                    .orElseThrow(() -> new NotFoundException("idProducto: " + idProducto + " ó idDetailsProduto :" + ide
                            + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

            return productMapper.mapperProInformationToDTO(productInformation);

        } catch (DataAccessException e) {
            LOGGER.error("Error buscar detalle producto", e);
            throw new NotDataAccessException("Error buscar detalle producto: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInformationDTO> findProductInforAll(Long idProducto) {

        Product product = findByIdPrivate(idProducto);

        try {

            List<ProductInformation> productInformations = product.getProductInformations();

            return productMapper
                    .mapperProInformationAToDTO(productInformations);

        } catch (DataAccessException e) {
            LOGGER.error("Error listar detalle producto", e);
            throw new NotDataAccessException("Error listar detalle producto: " + e.getMessage());

        }

    }

    @Override
    public ProductInformationDTO updateProductInfo(ProductInformationDTO productInformationDTO, Long ide) {

        try {

            ProductInformation productInformation = productInformationRepository.findById(ide)
                    .orElseThrow(() -> new NotFoundException(""));

            productInformation
                    .setAttribute(productInformationDTO.getAttribute() == null ? productInformation.getAttribute()
                            : productInformationDTO.getAttribute());

            productInformation.setValue(productInformationDTO.getValue() == null ? productInformation.getValue()
                    : productInformationDTO.getValue());

            ProductInformation productInformationUpdated = productInformationRepository.save(productInformation);

            return productMapper.mapperProInformationToDTO(productInformationUpdated);

        } catch (DataAccessException e) {
            LOGGER.error("Error actualizar  detalle producto", e);
            throw new NotDataAccessException("Error actualizar detalle producto: " + e.getMessage());
        }

    }

    @Override
    public TaxProduct finTaxProduct(TypeTaxEnum typeTaxEnum, String codeImpuesto, Product product) {

        TaxValue taxValue = taxValueRepository.findByCode(codeImpuesto).orElseThrow(
                () -> new NotFoundException(typeTaxEnum.name() + " con codigo " + codeImpuesto + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

        AdminProduct.calcularNuevoPrecioProdImpuesto(typeTaxEnum, product, taxValue.getPorcentage());

        return new TaxProduct(product, taxValue);

    }

    @Override
    @Transactional
    public String deleteProductInfoById(Long idProducto, Long ide) {

        // ProductInformation productInformation = findProductInforByIdPrivate(ide);

        try {

            Integer rowDelete = productInformationRepository.deleteProductInformation(idProducto, ide);

            if (rowDelete > 0) {

                return "Detalle producto eliminado con exito";
            }

            // productInformationRepository.deleteById(productInformation.getIde());

            throw new NotFoundException("idProducto: " + idProducto + " ó idDetailsProduto :" + ide
                    + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
        } catch (DataAccessException e) {
            LOGGER.error("Error eliminar detalle producto", e);
            throw new NotDataAccessException("Error eliminar detalle producto: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public ProductInformation findProductInforByIdPrivate(Long ide) {
        try {

            return productInformationRepository.findById(ide).orElseThrow(
                    () -> new NotFoundException("id: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
        } catch (DataAccessException e) {
            LOGGER.error("Error buscar detalle producto", e);
            throw new NotDataAccessException("Error buscar detalle producto: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public String deleteProductInfoAllById(Long idProducto) {

        Product product = findByIdPrivate(idProducto);

        try {

            Integer rowDelete = productInformationRepository.deleteProductInformationAll(product.getIde());

            return "Se eliminarón " + rowDelete + " registros";

        } catch (DataAccessException e) {
            LOGGER.error("Error eliminar detalle producto", e);
            throw new NotDataAccessException("Error eliminar detalle producto: " + e.getMessage());
        }

    }
}
