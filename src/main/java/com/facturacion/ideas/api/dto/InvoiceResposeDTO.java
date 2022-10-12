package com.facturacion.ideas.api.dto;

public class InvoiceResposeDTO extends DocumentResponseDTO {

	private static final long serialVersionUID = 1L;

	private String remissionGuideNumber;

	public InvoiceResposeDTO() {
		super();
	}

	public InvoiceResposeDTO(Long ide, String typeDocument, String numberSecuencial, String keyAccess,
			String numberAutorization, String dateAutorization, String dateEmission, String typoEmision,
			String remissionGuideNumber) {
		super(ide, typeDocument, numberSecuencial, keyAccess, numberAutorization, dateAutorization, dateEmission,
				typoEmision);
		this.remissionGuideNumber = remissionGuideNumber;
	}

	public String getRemissionGuideNumber() {
		return remissionGuideNumber;
	}

	public void setRemissionGuideNumber(String remissionGuideNumber) {
		this.remissionGuideNumber = remissionGuideNumber;
	}

	@Override
	public String toString() {
		return "InvoiceResposeDTO [remissionGuideNumber=" + remissionGuideNumber + ", toString()=" + super.toString() + "]";
	}

}
