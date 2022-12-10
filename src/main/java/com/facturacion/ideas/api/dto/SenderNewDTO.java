package com.facturacion.ideas.api.dto;

import java.io.Serializable;
import java.util.List;

import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeSenderEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SenderNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long ide;

	private String ruc;

	@NotBlank(message = "La razón social no pueder estar vacío")
	@Size(min = 2, max =  200, message = "La razón social debe tener entre 2 y 200 caracteres")
	private String socialReason;

	@Size(max = 200, message = "EL nombre comercial debe tener maximo 200 caracteres")
	private String commercialName;

	@NotBlank(message = "La dirección del establecimiento no pueder estar vacío")
	@Size(min = 2, max =  200, message = "La dirección del establecimiento debe tener entre 2 y 200 caracteres")
	private String matrixAddress;

	//@Size(max = 5, message = "EL código de contribuyente especial debe tener entre 3 y 5 dígitos")
	private String specialContributor;
	
	private String  retentionAgent;

	private String accountancy;

	private String typeSender;

	@NotBlank(message = "El tipo de ambiente no pueder ser vacío")
	@Size(min = 1, max = 1, message = "EL tipo de ambiente debte tener solo 1 dígito")
	private String typeEnvironment;

	@NotBlank(message = "El tipo de emisión no pueder ser vacío")
	@Size(min = 1, max = 1, message = "El tipo de emisión debe tener solo 1 dígito")
	private String typeEmission;

	//@Max(min = 3, value = 100, message = "La contraseña de la firma dígital puede tener hasta 100 caracteres")
	private String passwordCerticate;

	private boolean rimpe;

	@Size( min = 2, max = 2, message = "El código de provincia debe tener 2 caracteres")
	private String province;

	//private List<EmailSenderNewDTO> emailSenderNewDTOList = new ArrayList<>();

	private String email;
	@NotBlank(message = "El código del establecimiento es obligatorio")
	@Size(min = 3, max = 3, message = "El codigo establecimiento debe tener 3 dígitos")
	private String subsidiary;


	@NotBlank(message = "El código del punto emisión es obligatorio")
	@Size(min = 3, max = 3, message = "El codigo punto emisión debe tener 3 dígitos")
	private String emisionPoint;

	public SenderNewDTO() {
	}

	public SenderNewDTO(Long ide, String ruc, String socialReason, String commercialName, String matrixAddress,
						String specialContributor, String  retentionAgent, String accountancy, String typeSender,
						String typeEnvironment, String typeEmission, boolean rimpe, String province) {
		super();
		this.ide = ide;
		this.ruc = ruc;
		this.socialReason = socialReason;
		this.commercialName = commercialName;
		this.matrixAddress = matrixAddress;
		this.specialContributor = specialContributor;
		this.retentionAgent = retentionAgent;
		this.accountancy = accountancy;
		this.typeSender = typeSender;

		this.typeEnvironment = typeEnvironment;
		this.typeEmission = typeEmission;
		this.rimpe = rimpe;
		this.province = province;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getSocialReason() {
		return socialReason;
	}

	public void setSocialReason(String socialReason) {
		this.socialReason = socialReason;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	public String getMatrixAddress() {
		return matrixAddress;
	}

	public void setMatrixAddress(String matrixAddress) {
		this.matrixAddress = matrixAddress;
	}

	public String getSpecialContributor() {
		return specialContributor;
	}

	public void setSpecialContributor(String specialContributor) {
		this.specialContributor = specialContributor;
	}
	
	

	public String getRetentionAgent() {
		return retentionAgent;
	}

	public void setRetentionAgent(String retentionAgent) {
		this.retentionAgent = retentionAgent;
	}

	public String getAccountancy() {
		return accountancy;
	}

	public void setAccountancy(String accountancy) {
		this.accountancy = accountancy;
	}

	public String getTypeSender() {
		return typeSender;
	}

	public void setTypeSender(String typeSender) {
		this.typeSender = typeSender;
	}

	public String getTypeEnvironment() {
		return typeEnvironment;
	}

	public void setTypeEnvironment(String typeEnvironment) {
		this.typeEnvironment = typeEnvironment;
	}

	public String getTypeEmission() {
		return typeEmission;
	}

	public void setTypeEmission(String typeEmission) {
		this.typeEmission = typeEmission;
	}

	public boolean isRimpe() {
		return rimpe;
	}

	public void setRimpe(boolean rimpe) {
		this.rimpe = rimpe;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordCerticate() {
		return passwordCerticate;
	}

	public void setPasswordCerticate(String passwordCerticate) {
		this.passwordCerticate = passwordCerticate;
	}

	public String getSubsidiary() {
		return subsidiary;
	}

	public void setSubsidiary(String subsidiary) {
		this.subsidiary = subsidiary;
	}

	public void setEmisionPoint(String emisionPoint) {
		this.emisionPoint = emisionPoint;
	}

	public String getEmisionPoint() {
		return emisionPoint;
	}

	@Override
	public String toString() {
		return "SenderNewDTO{" +
				"ide=" + ide +
				", ruc='" + ruc + '\'' +
				", socialReason='" + socialReason + '\'' +
				", commercialName='" + commercialName + '\'' +
				", matrixAddress='" + matrixAddress + '\'' +
				", specialContributor='" + specialContributor + '\'' +
				", retentionAgent='" + retentionAgent + '\'' +
				", accountancy=" + accountancy +
				", typeSender=" + typeSender +
				", email=" + email +
				", typeEnvironment='" + typeEnvironment + '\'' +
				", typeEmission='" + typeEmission + '\'' +
				", passwordCerticate='" + passwordCerticate + '\'' +
				", rimpe=" + rimpe +
				", province='" + province + '\'' +
				", emailSenderNewDTOList=" + email +
				", subsidiary='" + subsidiary + '\'' +
				", emisionPoint='" + emisionPoint + '\'' +
				'}';
	}
}
