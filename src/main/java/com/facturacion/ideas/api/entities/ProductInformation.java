package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INFO_ADICIONAL")
public class ProductInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INF_COD")
	private Long ide;

	@Column(name = "INF_ATR")
	private String attribute;

	@Column(name = "INF_VAL")
	private String value;

	public ProductInformation() {
		super();
	}

	public ProductInformation(Long ide, String attribute, String value) {
		super();
		this.ide = ide;
		this.attribute = attribute;
		this.value = value;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ProductInformation [ide=" + ide + ", attribute=" + attribute + ", value=" + value + "]";
	}

}
