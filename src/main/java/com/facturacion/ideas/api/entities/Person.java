package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeIdentificationEnum;

@Entity
@Table(name = "PERSONAS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PER_COD")
	private Long ide;

	@Column(name = "PER_TIP_IDT")
	private String typeIdentification;

	@Column(name = "PER_NUM_IDT")
	private String numberIdentification;

	@Column(name = "PER_RAZ_SOC")
	private String socialReason;

	@Column(name = "PER_EMA")
	private String email;

	@Column(name = "PER_DIR")
	private String address;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person")
	private List<DetailsPerson> detailsPersons;

	public Person() {
		super();
		initData();
	}

	public Person(Long ide, TypeIdentificationEnum typeIdentification, String numberIdentification, String socialReason, String email,
			String address) {
		super();
		this.ide = ide;
		this.typeIdentification = typeIdentification.getCode();
		this.numberIdentification = numberIdentification;
		this.socialReason = socialReason;
		this.email = email;
		this.address = address;
	}

	private void initData() {

		detailsPersons = new ArrayList<>();
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getTipoIdentificacion() {
		return typeIdentification;
	}

	public void setTipoIdentificacion(TypeIdentificationEnum typeIdentificationEnum) {
		this.typeIdentification = typeIdentificationEnum.getCode();
	}

	public String getNumeroIdentificacion() {
		return numberIdentification;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numberIdentification = numeroIdentificacion;
	}

	public String getRazonSocial() {
		return socialReason;
	}

	public void setRazonSocial(String razonSocial) {
		this.socialReason = razonSocial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<DetailsPerson> getDetailsPersons() {
		return detailsPersons;
	}

	public void addDetailsPerson(DetailsPerson detailsPerson) {

		this.detailsPersons.add(detailsPerson);
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "Person [ide=" + ide + ", typeIdentification=" + typeIdentification + ", numberIdentification="
				+ numberIdentification + ", socialReason=" + socialReason + ", email=" + email + ", address=" + address
				+ "]";
	}

}
