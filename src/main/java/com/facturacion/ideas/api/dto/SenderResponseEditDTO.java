package com.facturacion.ideas.api.dto;

import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeSenderEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SenderResponseEditDTO implements Serializable {

    //private Long ide;

   // private String ruc;

    private String socialReason;

    private String commercialName;

    private String matrixAddress;

    private String specialContributor;

    private String  retentionAgent;

    private QuestionEnum accountancy;

    private TypeSenderEnum typeSender;

    private String logo;

    private String typeEnvironment;

    private String typeEmission;

    private String passwordCerticate;

    private String nameCerticate;

    private boolean rimpe;

    private String province;

    private List<EmailSenderNewDTO> emailSenderNewDTOList = new ArrayList<>();

    private String subsidiary;

    private String emisionPoint;


    public  SenderResponseEditDTO(){}


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

    public QuestionEnum getAccountancy() {
        return accountancy;
    }

    public void setAccountancy(QuestionEnum accountancy) {
        this.accountancy = accountancy;
    }

    public TypeSenderEnum getTypeSender() {
        return typeSender;
    }

    public void setTypeSender(TypeSenderEnum typeSender) {
        this.typeSender = typeSender;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getPasswordCerticate() {
        return passwordCerticate;
    }

    public void setPasswordCerticate(String passwordCerticate) {
        this.passwordCerticate = passwordCerticate;
    }

    public String getNameCerticate() {
        return nameCerticate;
    }

    public void setNameCerticate(String nameCerticate) {
        this.nameCerticate = nameCerticate;
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

    public List<EmailSenderNewDTO> getEmailSenderNewDTOList() {
        return emailSenderNewDTOList;
    }

    public void setEmailSenderNewDTOList(List<EmailSenderNewDTO> emailSenderNewDTOList) {
        this.emailSenderNewDTOList = emailSenderNewDTOList;
    }

    public String getSubsidiary() {
        return subsidiary;
    }

    public void setSubsidiary(String subsidiary) {
        this.subsidiary = subsidiary;
    }

    public String getEmisionPoint() {
        return emisionPoint;
    }

    public void setEmisionPoint(String emisionPoint) {
        this.emisionPoint = emisionPoint;
    }

    @Override
    public String toString() {
        return "SenderResponseEditDTO{" +
                "socialReason='" + socialReason + '\'' +
                ", commercialName='" + commercialName + '\'' +
                ", matrixAddress='" + matrixAddress + '\'' +
                ", specialContributor='" + specialContributor + '\'' +
                ", retentionAgent='" + retentionAgent + '\'' +
                ", accountancy=" + accountancy +
                ", typeSender=" + typeSender +
                ", logo='" + logo + '\'' +
                ", typeEnvironment='" + typeEnvironment + '\'' +
                ", typeEmission='" + typeEmission + '\'' +
                ", passwordCerticate='" + passwordCerticate + '\'' +
                ", nameCerticate='" + nameCerticate + '\'' +
                ", rimpe=" + rimpe +
                ", province='" + province + '\'' +
                ", emailSenderNewDTOList=" + emailSenderNewDTOList.size() +
                ", subsidiary='" + subsidiary + '\'' +
                ", emisionPoint='" + emisionPoint + '\'' +
                '}';
    }
}

