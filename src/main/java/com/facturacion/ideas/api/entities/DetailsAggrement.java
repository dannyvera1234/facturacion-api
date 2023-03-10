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

@Entity
@Table(name = "CONTRA_PLANES")
public class DetailsAggrement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CTP_COD")
	private Long ide;

	@Column(name = "CTP_FEC_INI")
	private Date dateStart;

	@Column(name = "CTP_FEC_FIN")
	private Date dateEnd;
	
	@Column(name="CTP_CAN")
	private int  amount;

	@Column(name = "CTP_EST")
	private boolean status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CTP_FK_COD_PLA")
	/*
	 * Relacion unidireccional,se creara una campo CTP_FK_COD_PLA en esta entidad
	 * contra_planes
	 */
	private Agreement greement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CTP_FK_COD_CUE")
	private Count count;

	public DetailsAggrement() {
		super();
	}

	public DetailsAggrement(Long ide, Date dateStart, Date dateEnd, boolean status, Agreement greement) {
		super();
		this.ide = ide;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.status = status;
		this.greement = greement;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Agreement getGreement() {
		return greement;
	}

	public void setGreement(Agreement greement) {
		this.greement = greement;
	}
	public void setCount(Count count) {
		
		this.count = count;
		// Asignar detalle a la cuenta
		this.count.addDetailsAggrement(this);
	}
	
	public Count getCount() {
		return count;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	

	@Override
	public String toString() {
		return "DetailsAggrement [ide=" + ide + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", status="
				+ status + ", greement=" + greement + "]";
	}

}
