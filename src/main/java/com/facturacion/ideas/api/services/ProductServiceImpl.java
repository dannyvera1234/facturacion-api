package com.facturacion.ideas.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.facturacion.ideas.api.admin.AdminProduct;
import com.facturacion.ideas.api.dto.ProductEditDTO;
import com.facturacion.ideas.api.dto.ProductResponseDTO;
import com.facturacion.ideas.api.enums.TypeTaxEnum;
import com.facturacion.ideas.api.repositories.*;
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

    @Autowired
    private ISenderRepository senderRepository;

    @Override
    @Transactional
    public ProductResponseDTO save(ProductDTO productDTO, boolean typeAction) {

        Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
        );

        Long idSubsidiary = subsidiaryRepository.findIdBySender(idSender).orElseThrow(
                () -> new NotFoundException("No existe un estblecimiento registrado actual")
        );

        /**
         * true: nuevo producto
         * false: actualziar producto
         */
        if (typeAction){

            if (existsByCodePrivateAndSubsidiaryIde(productDTO.getCodePrivate(), idSubsidiary)) {
                throw new DuplicatedResourceException("Producto " + productDTO.getCodePrivate() + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);
            }
        }

        // Actualizar
        if (!typeAction){

            if (!productRepository.existsById(productDTO.getIde())){
                throw new DuplicatedResourceException("Producto " + productDTO.getIde() + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
            }
        }
        AdminProduct.numeroMaximoInfoAdicionalProduct(productDTO.getProductInformationDTOs());

        try {
            Product product = productMapper.mapperToEntity(productDTO);

            // Lista impuesto
            List<TaxProduct> taxValues = new ArrayList<>();

            // IVA
            String codeImpuesto = product.getIva(); // codigo impuesto IVA, tipo de iva
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
            product.setSubsidiary(new Subsidiary(idSubsidiary));

            // Asignar impuestos
            product.setTaxProducts(taxValues);

            return productMapper.mapperToDTO(productRepository.save(product));

        } catch (DataAccessException e) {

            LOGGER.info("Error " + (typeAction?"guardar":"actualizar") +   "producto", e);
            throw new NotDataAccessException("Error" + (typeAction?"guardar":"actualizar") + " producto: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long idProduct) {

        try {

            if (senderRepository.existsByRuc(ConstanteUtil.TOKEN_USER)) {

                Product product = productRepository.findById(idProduct)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        String.format("Producto %s %s", idProduct, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));

                return productMapper.mapperToDTO(product);


            }
            throw new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));


        } catch (DataAccessException e) {

            LOGGER.error("Error buscar por id producto: " + idProduct, e);
            throw new NotDataAccessException("Error al buscar el producto por ide");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEditDTO fetchTaxValueAndInfoDetailsById(Long idProduct) {

        try {

            LOGGER.info("TOken: " + ConstanteUtil.TOKEN);
            LOGGER.info("TOken USER: " + ConstanteUtil.TOKEN_USER);

            if (senderRepository.existsByRuc(ConstanteUtil.TOKEN_USER)) {

                Product product = productRepository.fetchTaxValueAndInfoDetailsById(idProduct)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        String.format("Producto %s %s", idProduct, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));


                LOGGER.debug(product);
                LOGGER.debug(product.getProductInformations().size());
                LOGGER.debug(product.getTaxProducts().size());

                return productMapper.mapperToEditDTO(product);
            }

            throw new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
        } catch (DataAccessException e) {

            LOGGER.error("Error buscar por id producto: " + idProduct, e);
            throw new NotDataAccessException("Error al buscar el producto para editar");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findByCodePrivate(String codePrivate) {
        try {
            return productMapper.mapperToDTO(productRepository.findByCodePrivate(codePrivate).orElseThrow(
                    () -> new NotFoundException("Producto codigo " + codePrivate + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));
        } catch (DataAccessException e) {

            LOGGER.error("Error buscar producto por codigo: " + codePrivate, e);
            throw new NotDataAccessException("Error al buscar el producto por codigo");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll(Long idSubsidiary) {

        try {


            if (!subsidiaryRepository.existsById(idSubsidiary)) {

                throw new NotFoundException("Establecimiento : " + idSubsidiary + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
            }

            List<Product> products = productRepository.findBySubsidiaryIde(idSubsidiary);

            return products.stream().map(item -> productMapper.mapperToDTO(item))
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {

            LOGGER.error("Error listar productos", e);
            throw new NotDataAccessException("Error listar productos: " + e.getMessage());

        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllBySender() {

        Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                ()-> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
        );

        try {

            List<Product> products = productRepository.findBySubsidiarySenderIde(idSender);

            return products.stream().map(item -> productMapper.mapperToDTO(item))
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {

            LOGGER.error("Error listar productos", e);
            throw new NotDataAccessException("Ocurrio un error al listar productos ");

        }
    }

    @Override
    @Transactional
    public String deleteById(Long idProduct) {

        try {

            if (senderRepository.existsByRuc(ConstanteUtil.TOKEN_USER)) {
                productRepository.deleteById(idProduct);
                return "Producto eliminado correctamente";
            }

            throw new NotFoundException("Emisor " + ConstanteUtil.TOKEN_USER + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
        } catch (DataAccessException e) {

            LOGGER.error("Error eliminar producto", e);
            throw new NotDataAccessException("Error eliminar producto: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public ProductResponseDTO update(ProductDTO productDTO, Long idProduct) {

        try {

            LOGGER.info("Token: " + ConstanteUtil.TOKEN);
            LOGGER.info("Token user: " + ConstanteUtil.TOKEN_USER);

            if (senderRepository.existsByRuc(ConstanteUtil.TOKEN_USER)) {

                Product product = productRepository.findById(idProduct).orElseThrow(() ->
                        new NotFoundException("Producto " + idProduct + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

                productMapper.mapperPreUpdate(product, productDTO);

                return productMapper.mapperToDTO(productRepository.save(product));
            }

            throw new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));


        } catch (DataAccessException e) {

            LOGGER.error("Error actualizar producto", e);
            throw new NotDataAccessException("Error actualizar producto: " + e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductInformationDTO> findProductInforAll(final Long idProducto) {

        try {
            Product product = productRepository.findById(idProducto).orElseThrow(
                    () -> new NotFoundException("Producto " + idProducto + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));


            List<ProductInformation> productInformations = product.getProductInformations();

            return productMapper
                    .mapperProInformationAToDTO(productInformations);

        } catch (DataAccessException e) {
            LOGGER.error("Error listar detalle producto", e);
            throw new NotDataAccessException("Error listar detalle producto: " + e.getMessage());

        }

    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByCodePrivateAndSubsidiaryIde(String codePrivate, Long idSubsidiary) {

        try {

            return productRepository.existsByCodePrivateAndSubsidiaryIde(codePrivate, idSubsidiary);
        } catch (DataAccessException e) {

            LOGGER.error("Error  verificar existencia producto", e);
            throw new NotDataAccessException("Error verificar existencia producto: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public ProductInformationDTO updateProductInfo(ProductInformationDTO productInformationDTO, final Long ide) {

        try {

            ProductInformation productInformation = productInformationRepository.findById(ide)
                    .orElseThrow(() -> new NotFoundException("Campo Adicional " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

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

        // AdminProduct.calcularNuevoPrecioProdImpuesto(typeTaxEnum,  taxValue ,  product);

        return new TaxProduct(product, taxValue);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchByCodeAndName(String filtro) {

        try {
            filtro = "%" + filtro + "%";

            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );

            Long idSubsidiary = subsidiaryRepository.findIdBySender(idSender).orElseThrow(
                    () -> new NotFoundException("No existe un estblecimiento registrado actual")
            );
            List<Product> products = productRepository.findBySubsidiaryAndName(idSubsidiary, filtro);

            return productMapper.mapperToDTO(products);

        } catch (DataAccessException e) {
            LOGGER.error("Error al filtrar productos", e);
            throw new NotDataAccessException("Error filrtar producto: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public String deleteProductInfoById(final Long idProducto, Long ide) {

        try {


            if (productInformationRepository.existsById(ide)) {
                 productInformationRepository.deleteById(ide);
                return "Informacion Adicional  eliminada con exito";
            }
            throw new NotFoundException("Detalle producto: " + ide
                    + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);


        } catch (DataAccessException e) {
            LOGGER.error("Error eliminar detalle producto", e);
            throw new NotDataAccessException("Error eliminar detalle producto: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public String deleteProductInfoAllById(final Long idProducto) {

        try {

            if (productRepository.existsById(idProducto)) {

                Integer rowDelete = productInformationRepository.deleteProductInformationAll(idProducto);

                return "Se eliminar??n " + rowDelete + " registros";

            }
            throw new NotFoundException("Producto " + idProducto + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);


        } catch (DataAccessException e) {
            LOGGER.error("Error eliminar detalle producto", e);
            throw new NotDataAccessException("Error eliminar detalle producto: " + e.getMessage());
        }

    }
}
