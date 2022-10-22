package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CODIGOS_NUMEROS")
public class CodeDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CON_COD")
	private Long code;

	@Column(name = "CON_COD_CUE")
	private Long codeCount;

	@Column(name = "CON_COD_EST")
	private String codeSubsidiary;

	@Column(name = "CON_NUM_EST")
	private Integer numSubsidiary;

	@Column(name = "CON_NUM_PTO_EMI")
	private Integer numEmissionPoint;

	public CodeDocument() {
		super();
	}

	public CodeDocument(Long code, Long codeCount, String codeSubsidiary, Integer numSubsidiary,
			Integer numEmissionPoint) {
		super();
		this.code = code;
		this.codeCount = codeCount;
		this.codeSubsidiary = codeSubsidiary;
		this.numSubsidiary = numSubsidiary;
		this.numEmissionPoint = numEmissionPoint;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getCodeCount() {
		return codeCount;
	}

	public void setCodeCount(Long codeCount) {
		this.codeCount = codeCount;
	}

	public String getCodeSubsidiary() {
		return codeSubsidiary;
	}

	public void setCodeSubsidiary(String codeSubsidiary) {
		this.codeSubsidiary = codeSubsidiary;
	}

	public Integer getNumSubsidiary() {
		return numSubsidiary;
	}

	public void setNumSubsidiary(Integer numSubsidiary) {
		this.numSubsidiary = numSubsidiary;
	}

	public Integer getNumEmissionPoint() {
		return numEmissionPoint;
	}

	public void setNumEmissionPoint(Integer numEmissionPoint) {
		this.numEmissionPoint = numEmissionPoint;
	}

	@Override
	public String toString() {
		return "CodeDocument [code=" + code + ", codeCount=" + codeCount + ", codeSubsidiary=" + codeSubsidiary
				+ ", numSubsidiary=" + numSubsidiary + ", numEmissionPoint=" + numEmissionPoint + "]";
	}

	
	
	
	
}
