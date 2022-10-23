package com.facturacion.ideas.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
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
		
		// Verifica existencia del Producto
		isExistProductBySubsidiary(productDTO.getCodePrivate(), subsidiary.getIde());
		
		
		
		
		

		// Verificar si el numero informaion adicional del producto no exceden en 3
		// registros
		if (productDTO.getProductInformationDTOs().size() > 3)
			throw new BadRequestException("Cantidad información adicional  producto exceden a 3 ; cantidad: "
					+ productDTO.getProductInformationDTOs().size());

		try {

			Product product = productMapper.mapperToEntity(productDTO);		
			
			product.setSubsidiary(subsidiary);

			
			// LIsta para guardar los impuesto de productos
			List<TaxProduct> taxValues = new ArrayList<>();
			
			// Asignar impuestos al producto
			
			// Indica que si se le asigno un impuesto
			if (product.getIva() != null) {
				
				// Obtener el code del impuesto valor, este valor no es que se guardara en la bd
				String codeImpuesto = product.getIva();
				
				// reempalzar codigo iva por, SI Indica que si tiene iva
				product.setIva(QuestionEnum.SI.name());
				
	
				TaxValue taxValue = taxValueRepository.findByCode(codeImpuesto).orElseThrow(
						() -> new NotFoundException("Iva con codigo " +  codeImpuesto +  ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
				
				TaxProduct taxProductIva = new TaxProduct();
				
				taxProductIva.setProduct(product);
				taxProductIva.setTaxValue(taxValue);
				
				taxValues.add(taxProductIva);
				
			}else product.setIva(QuestionEnum.NO.name());
			
			
			// Indica que si se le asigno un impuesto
			if (product.getIce() != null) {
				
				// Obtener el codigo del impuesto valor, este valor no es que se guardara en la bd
				String codeImpuesto = product.getIce();
				
				// reempalzar codigo iva por, SI Indica que si tiene iva
				product.setIce(QuestionEnum.SI.name());
				
	
				TaxValue taxValue = taxValueRepository.findByCode(codeImpuesto).orElseThrow(
						() -> new NotFoundException("ICE con codigo " +  codeImpuesto +  ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
				
				TaxProduct taxProduct = new TaxProduct();
				
				taxProduct.setProduct(product);
				taxProduct.setTaxValue(taxValue);
				
				taxValues.add(taxProduct);
				
			}else product.setIce(QuestionEnum.NO.name());
			
			
			// Indica que si se le asigno un impuesto
			if (product.getIrbpnr() != null) {
				
				// Obtener el codigo del impuesto valor, este valor no es que se guardara en la bd
				String codeImpuesto = product.getIrbpnr();
				
				// reempalzar codigo iva por, SI Indica que si tiene iva
				product.setIrbpnr(QuestionEnum.SI.name());
				
	
				TaxValue taxValue = taxValueRepository.findByCode(codeImpuesto).orElseThrow(
						() -> new NotFoundException("IRBPN con codigo " +  codeImpuesto +  ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
				
				TaxProduct taxProduct = new TaxProduct();
				
				taxProduct.setProduct(product);
				taxProduct.setTaxValue(taxValue);
				
				taxValues.add(taxProduct);
				
			}else product.setIrbpnr(QuestionEnum.NO.name());
			
			
			// Asignar lista de impuesto al producto
			product.setTaxProducts(taxValues);
			
			ProductDTO productDTOSaved = productMapper.mapperToDTO(productRepository.save(product));

			return productDTOSaved;

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

			List<ProductDTO> productDTOs = products.stream().map(item -> productMapper.mapperToDTO(item))
					.collect(Collectors.toList());

			return productDTOs;

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
			ProductDTO productDTOSaved = productMapper.mapperToDTO(productRepository.save(product));

			return productDTOSaved;

		} catch (DataAccessException e) {

			LOGGER.error("Error actualizar producto", e);
			throw new NotDataAccessException("Error actualizar producto: " + e.getMessage());
		}

	}

	@Transactional(readOnly = true)
	private Subsidiary findSubsidiaryByIdPrivate(Long ide) {

		try {

			Subsidiary subsidiary = subsidiaryRepository.findById(ide).orElseThrow(
					() -> new NotFoundException("Id: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return subsidiary;

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar establecimiento", e);
			throw new NotDataAccessException("Error buscar establecimiento: " + e.getMessage());
		}

	}

	/**
	 * Verifica si un producto existe en un establecimiento
	 */
	@Transactional(readOnly = true)
	private void isExistProductBySubsidiary(String codePrivateProd, Long idSubsidiary) {

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
	private Product findByIdPrivate(Long ide) {

		try {

			Product product = productRepository.findById(ide).orElseThrow(
					() -> new NotFoundException("Id: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return product;

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

			List<ProductInformationDTO> productInformationDTOs = productMapper
					.mapperProInformationAToDTO(productInformations);

			return productInformationDTOs;

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
	private ProductInformation findProductInforByIdPrivate(Long ide) {
		try {

			ProductInformation productInformation = productInformationRepository.findById(ide).orElseThrow(
					() -> new NotFoundException("id: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return productInformation;
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
