package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;

@Service
public class CodeDocumentServiceImpl implements ICodeDocumentService {

	@Autowired
	private ICodeDocumentRepository codeDocumentRepository;

	@Override
	@Transactional
	public CodeDocument save(CodeDocument codeDocument) {

		return codeDocumentRepository.save(codeDocument);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CodeDocument> findById(Long id) {

		return codeDocumentRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CodeDocument> findAll() {
		return codeDocumentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Integer> findNumberMaxByIdCount(Long idCount) {

		return codeDocumentRepository.findNumberMaxSender(idCount);
	}

	@Override
	public Optional<CodeDocument> findByIdSenderAndCodSubsidiary(Long idSender, String codSubsidiary) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	@Transactional
	public void deleteByIdCount(Long idCount) {

		codeDocumentRepository.deleteByCodeCount(idCount);
	}

	@Override
	@Transactional
	public void deleteByIdCountAndCodeSubsidiary(Long idCount, String codeSender) {

		codeDocumentRepository.deleteByIdCountAndCodeSubsidiary(idCount, codeSender);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CodeDocument> findByCodeCountAndCodeSubsidiary(Long codeCount, String codeSubsidiary) {
	
		return codeDocumentRepository.findByCodeCountAndCodeSubsidiary(codeCount, codeSubsidiary);
	}

}
