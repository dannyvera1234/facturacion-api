package com.facturacion.ideas.api.dto;

public class InvoiceNewDTO extends DocumentNewDTO {

	private static final long serialVersionUID = 1L;

	private String remissionGuideNumber;

	public InvoiceNewDTO() {
		super();
	}

	public InvoiceNewDTO(Long ide, String typeDocument, String numberSecuencial, String keyAccess,
			String numberAutorization, String dateAutorization, String dateEmission, String typoEmision, Long idPerson,
			Long idEmissionPoint, String remissionGuideNumber) {
		super(ide, typeDocument, numberSecuencial, keyAccess, numberAutorization, dateAutorization, dateEmission,
				typoEmision, idPerson, idEmissionPoint);
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
		return "InvoiceNewDTO [remissionGuideNumber=" + remissionGuideNumber + ", toString()=" + super.toString() + "]";
	}

}
