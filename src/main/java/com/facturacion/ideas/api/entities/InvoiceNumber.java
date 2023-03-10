package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeDocumentEnum;

@Entity
@Table(name="NUMEROS_FACTURA")
public class InvoiceNumber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NUF_COD")
	private Long ide;

	@Column(name="NUF_TIP_DOC")
	private String typeDocument;
	
	@Column(name="NUF_NUM")
	private int currentSequentialNumber;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NUF_FK_COD_PTE")
	private EmissionPoint emissionPoint;

	

	public InvoiceNumber(Long ide, String typeDocument, int currentSequentialNumber, EmissionPoint emissionPoint) {
		super();
		this.ide = ide;
		this.typeDocument = typeDocument;
		this.currentSequentialNumber = currentSequentialNumber;
		this.emissionPoint = emissionPoint;
	}

	public InvoiceNumber() {
		super();
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public int getCurrentSequentialNumber() {
		return currentSequentialNumber;
	}

	public void setCurrentSequentialNumber(int currentSequentialNumber) {
		this.currentSequentialNumber = currentSequentialNumber;
	}

	public EmissionPoint getEmissionPoint() {
		return emissionPoint;
	}

	public void setEmissionPoint(EmissionPoint emissionPoint) {
		this.emissionPoint = emissionPoint;
	}
	
	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocumentEnum typeDocument) {
		this.typeDocument = typeDocument.getCode();
	}

	@Override
	public String toString() {
		return "InvoiceNumber [ide=" + ide + ", currentSequentialNumber=" + currentSequentialNumber + "]";
	}

}
