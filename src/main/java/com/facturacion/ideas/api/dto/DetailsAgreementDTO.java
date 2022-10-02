package com.facturacion.ideas.api.dto;

import java.io.Serializable;

public class DetailsAgreementDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ide;

	private String dateStart;

	private String dateEnd;

	private boolean status;

	private String agreement;

	public DetailsAgreementDTO() {
		super();
	}

	public DetailsAgreementDTO(Long ide, String dateStart, String dateEnd, boolean status) {
		super();
		this.ide = ide;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.status = status;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	@Override
	public String toString() {
		return "DetailsAgreementDTO [ide=" + ide + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", status="
				+ status + "]";
	}

}
