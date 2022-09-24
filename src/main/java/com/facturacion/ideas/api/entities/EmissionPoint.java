package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "puntos_emision")
public class EmissionPoint implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PTE_COD")
	private Long ide;

	@Column(name = "PTE_COD_EMI")
	private String codePoint;

	@Column(name = "PTE_EST")
	private boolean status;

	@Column(name = "PTE_FEC")
	private Date dateRegister;

	@Column(name = "PTE_CLA")
	private String keyPoint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PTE_FK_COD_EST")
	@JsonBackReference
	private Subsidiary subsidiary;

	public EmissionPoint() {
	}
	
	public EmissionPoint(Long ide, String codePoint, boolean status, Date dateRegister, String keyPoint,
			Subsidiary subsidiary) {

		this.ide = ide;
		this.codePoint = codePoint;
		this.status = status;
		this.dateRegister = dateRegister;
		this.keyPoint = keyPoint;
		this.subsidiary = subsidiary;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getCodePoint() {
		return codePoint;
	}

	public void setCodePoint(String codePoint) {
		this.codePoint = codePoint;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}

	public String getKeyPoint() {
		return keyPoint;
	}

	public void setKeyPoint(String keyPoint) {
		this.keyPoint = keyPoint;
	}

	public Subsidiary getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(Subsidiary subsidiary) {
		this.subsidiary = subsidiary;
	}

	@Override
	public String toString() {
		return "EmissionPoint [ide=" + ide + ", codePoint=" + codePoint + ", status=" + status + ", dateRegister="
				+ dateRegister + ", keyPoint=" + keyPoint + ", subsidiary=" + subsidiary.getCode() + "]";
	}

	
	

}
