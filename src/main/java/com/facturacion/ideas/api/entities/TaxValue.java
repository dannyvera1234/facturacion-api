package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="IMPUESTO_VALOR")
public class TaxValue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IMV_COD")
	private Long ide;

	@Column(name="IMV_COD_VAL")
	private String code;
	
	@Column(name="IMV_POR")
	private double porcentage;

	@Column(name="IMV_POR_RET")
	private double retentionPorcentage;

	@Column(name="IMV_TIP_IMP")
	private String typeTax;

	@Column(name="IMV_DES")
	private String description;

	@Column(name="IMV_FEC_INI")
	private String srtartDate;

	@Column(name="IMV_FEC_FIN")
	private String endDate;

	@Column(name="IMV_COD_ADM")
	private  int codAdmin;
	
	@Column(name="IMV_MAR_POR_LIB")
	private String porcentageMarkFree;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="IMV_FK_COD_IMP")
	private Tax tax;
	
	public TaxValue() {
		super();
	}

	public TaxValue(Long ide, String code, double porcentage, double retentionPorcentage, String typeTax,
			String description, String srtartDate, String endDate, String porcentageMarkFree) {
		super();
		this.ide = ide;
		this.code = code;
		this.porcentage = porcentage;
		this.retentionPorcentage = retentionPorcentage;
		this.typeTax = typeTax;
		this.description = description;
		this.srtartDate = srtartDate;
		this.endDate = endDate;
		this.porcentageMarkFree = porcentageMarkFree;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPorcentage() {
		return porcentage;
	}

	public void setPorcentage(double porcentage) {
		this.porcentage = porcentage;
	}

	public double getRetentionPorcentage() {
		return retentionPorcentage;
	}

	public void setRetentionPorcentage(double retentionPorcentage) {
		this.retentionPorcentage = retentionPorcentage;
	}

	public String getTypeTax() {
		return typeTax;
	}

	public void setTypeTax(String typeTax) {
		this.typeTax = typeTax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSrtartDate() {
		return srtartDate;
	}

	public void setSrtartDate(String srtartDate) {
		this.srtartDate = srtartDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPorcentageMarkFree() {
		return porcentageMarkFree;
	}

	public void setPorcentageMarkFree(String porcentageMarkFree) {
		this.porcentageMarkFree = porcentageMarkFree;
	}
	
	public void setTax(Tax tax) {
		this.tax = tax;
	}
	
	public Tax getTax() {
		return tax;
	}

	
	public int getCodAdmin() {
		return codAdmin;
	}

	public void setCodAdmin(int codAdmin) {
		this.codAdmin = codAdmin;
	}

	@Override
	public String toString() {
		return "TaxValue [ide=" + ide + ", code=" + code + ", porcentage=" + porcentage + ", retentionPorcentage="
				+ retentionPorcentage + ", typeTax=" + typeTax + ", description=" + description + ", srtartDate="
				+ srtartDate + ", EndDate=" + endDate + ", codAdmin=" + codAdmin + ", porcentageMarkFree="
				+ porcentageMarkFree + ", tax=" +  "]";
	}

	
	

}
