package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FORMAS_PAGO")
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FPG_COD_PAG")
	private String code;

	@Column(name = "FPG_DES")
	private String description;

	@Column(name = "FPG_FEC_INI")
	private Date dateStart;

	@Column(name = "FPG_FEC_FIN")
	private Date dateEnd;

	public Payment() {
		super();
	}

	public Payment(String code) {
		super();
		this.code = code;
	}

	public Payment(String code, String description, Date dateStart, Date dateEnd) {
		super();
		this.code = code;
		this.description = description;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "Payment [ code=" + code + ", description=" + description + ", dateStart=" + dateStart
				+ ", dateEnd=" + dateEnd + "]";
	}
	
	
	

}
