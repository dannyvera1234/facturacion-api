package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ESTABLECIMIENTOS")
public class Subsidiary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EST_COD")
	private Long ide;

	@Column(name = "EST_COD_EST")
	private String code;

	@Column(name = "EST_RAZ_SOC")
	private String socialReason;

	@Column(name = "EST_DIR")
	private String address;

	@Column(name = "EST_EST")
	private boolean status;

	@Column(name = "EST_FEC")
	private Date dateCreate;

	@Column(name = "EST_PRIN")
	private boolean principal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EST_FK_COD_EMI")
	@JsonBackReference
	private Sender sender;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subsidiary")
	@JsonManagedReference
	private List<EmissionPoint> emissionPoints;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subsidiary")
	//@JsonManagedReference
	private List<Employee> employees;

	public Subsidiary() {
		super();
		initData();
	}

	public Subsidiary(Long ide) {
		super();
		this.ide = ide;
		initData();
	}

	public Subsidiary(Long ide, String code, String socialReason, String address, boolean status, Date dateCreate,
			boolean principal) {
		super();
		this.ide = ide;
		this.code = code;
		this.socialReason = socialReason;
		this.address = address;
		this.status = status;
		this.dateCreate = dateCreate;
		this.principal = principal;
		initData();
	}

	private void initData() {
		emissionPoints = new ArrayList<>();
		employees = new ArrayList<>();
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

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public void addEmissionPoint(EmissionPoint emissionPoint) {

		emissionPoint.setSubsidiary(this);
		this.emissionPoints.add(emissionPoint);
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {

		this.employees.add(employee);
	}
	public List<EmissionPoint> getEmissionPoints() {
		return emissionPoints;
	}

	@Override
	public String toString() {
		return "Subsidiary [ide=" + ide + ", code=" + code + ", socialReason=" + socialReason + ", address=" + address
				+ ", status=" + status + ", dateCreate=" + dateCreate + ", principal=" + principal + "]";
	}

}
