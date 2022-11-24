package com.facturacion.ideas.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

public class AgreementDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;

	@NotBlank(message = "El tipo de plan no puede ser vacío")
	private String typeAgreement;

	@PositiveOrZero(message = "El precio de plan no pueder ser negativo")
	private double value;

	public AgreementDTO(Long codigo, String typeAgreement, double value) {
		super();
		this.codigo = codigo;
		this.typeAgreement = typeAgreement;
		this.value = value;
	}

	public AgreementDTO() {
		super();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getTypeAgreement() {
		return typeAgreement;
	}

	public void setTypeAgreement(String typeAgreement) {
		this.typeAgreement = typeAgreement;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AgreementDTO [codigo=" + codigo + ", typeAgreement=" + typeAgreement + ", value=" + value + "]";
	}

}
