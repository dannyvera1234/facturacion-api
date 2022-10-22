package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeDocumentEnum;

@Entity
@Table(name = "DOCUMENTOS")
@Inheritance(strategy = InheritanceType.JOINED)
public  abstract class Document implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DOC_COD")
	private Long ide;

	@Column(name = "DOC_COD_TIP")
	private String typeDocument;

	@Column(name = "DOC_NUM_SECU")
	private String numberSecuencial;

	@Column(name = "DOC_CLA_ACC")
	private String keyAccess;

	@Column(name = "DOC_NUM_AUT")
	private String numberAutorization;

	@Column(name = "DOC_FEC_AUT")
	private Date dateAutorization;

	@Column(name = "DOC_FEC_EMI")
	private Date dateEmission;

	@Column(name = "DOC_TIP_EMI")
	private String typoEmision;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_FK_COD_PTE")
	private EmissionPoint emissionPoint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_FK_COD_PER")
	private Person person;

	public Document() {
		super();
	}

	public Document(Long ide, TypeDocumentEnum typeDocument, String numberSecuencial, String keyAccess,
			String numberAutorization, Date dateAutorization, Date dateEmission, String typoEmision) {
		super();
		this.ide = ide;
		this.typeDocument = typeDocument.getCode();
		this.numberSecuencial = numberSecuencial;
		this.keyAccess = keyAccess;
		this.numberAutorization = numberAutorization;
		this.dateAutorization = dateAutorization;
		this.dateEmission = dateEmission;
		this.typoEmision = typoEmision;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocumentEnum typeDocument) {
		this.typeDocument = typeDocument.getCode();
	}

	public String getNumberSecuencial() {
		return numberSecuencial;
	}

	public void setNumberSecuencial(String numberSecuencial) {
		this.numberSecuencial = numberSecuencial;
	}

	public String getKeyAccess() {
		return keyAccess;
	}

	public void setKeyAccess(String keyAccess) {
		this.keyAccess = keyAccess;
	}

	public String getNumberAutorization() {
		return numberAutorization;
	}

	public void setNumberAutorization(String numberAutorization) {
		this.numberAutorization = numberAutorization;
	}

	public Date getDateAutorization() {
		return dateAutorization;
	}

	public void setDateAutorization(Date dateAutorization) {
		this.dateAutorization = dateAutorization;
	}

	public Date getDateEmission() {
		return dateEmission;
	}

	public void setDateEmission(Date dateEmission) {
		this.dateEmission = dateEmission;
	}

	public String getTypoEmision() {
		return typoEmision;
	}

	public void setTypoEmision(String typoEmision) {
		this.typoEmision = typoEmision;
	}

	public EmissionPoint getEmissionPoint() {
		return emissionPoint;
	}

	public void setEmissionPoint(EmissionPoint emissionPoint) {
		this.emissionPoint = emissionPoint;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Document [ide=" + ide + ", typeDocument=" + typeDocument + ", numberSecuencial=" + numberSecuencial
				+ ", keyAccess=" + keyAccess + ", numberAutorization=" + numberAutorization + ", dateAutorization="
				+ dateAutorization + ", dateEmission=" + dateEmission + ", typoEmision=" + typoEmision + "]";
	}

}
