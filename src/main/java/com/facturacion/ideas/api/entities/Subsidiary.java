package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "establecimientos")
public class Subsidiary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EST_COD")
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
	@JoinColumn(name="EST_FK_COD_EMI")
	@JsonBackReference
	private Sender sender;

	
	public Subsidiary() {
		super();
	}

	public Subsidiary(String code, String socialReason, String address, boolean status, Date dateCreate,
			boolean principal) {
		super();
		this.code = code;
		this.socialReason = socialReason;
		this.address = address;
		this.status = status;
		this.dateCreate = dateCreate;
		this.principal = principal;
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
	
	
	
	
}
