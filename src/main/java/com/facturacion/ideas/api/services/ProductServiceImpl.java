package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IProductMapper;
import com.facturacion.ideas.api.repositories.IProductRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class ProductServiceImpl implements IProductService {

	private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);
	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ISubsidiaryService subsidiaryService;

	@Autowired
	private IProductMapper productMapper;

	@Override
	public ProductDTO save(ProductDTO productDTO, Long idSubsidiary) {

		Subsidiary subsidiary = findSubsidiaryByIdPrivate(idSubsidiary);
		isExistProductBySubsidiary(productDTO.getCodePrivate(), subsidiary.getIde());

		try {

			Product product = productMapper.mapperToEntity(productDTO);
			product.setSubsidiary(subsidiary);

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

			Subsidiary subsidiary = subsidiaryService.findById(ide).orElseThrow(
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
}
