package com.facturacion.ideas.api.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.ProvinceEnum;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeEmissionEnum;
import com.facturacion.ideas.api.enums.TypeEnvironmentEnum;
import com.facturacion.ideas.api.enums.TypeSenderEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "EMISORES")
public class Sender implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMI_COD")
    private Long ide;

    @Column(name = "EMI_RUC")
    private String ruc;

    @Column(name = "EMI_RAZ_SOC")
    private String socialReason;

    @Column(name = "EMI_NOM_COM")
    private String commercialName;

    @Column(name = "EMI_DIR_MAT")
    private String matrixAddress;

    @Column(name = "EMI_CON_ESP")
    private String specialContributor;

    @Column(name = "EMI_AGE_RET")
    private String retentionAgent;

    @Column(name = "EMI_OBL_CON")
    @Enumerated(EnumType.STRING)
    private QuestionEnum accountancy;

    @Column(name = "EMI_TIP")
    @Enumerated(EnumType.STRING)
    private TypeSenderEnum typeSender;

    @Column(name = "EMI_LOG")
    private String logo;

    @Column(name = "EMI_TIP_AMB")
    // @Enumerated(EnumType.STRING)
    private String typeEnvironment;

    @Column(name = "EMI_TIP_EMI")
    private String typeEmission;

    @Column(name = "EMI_REG_RIM")
    private boolean rimpe;

    @Column(name = "EMI_PRO")
    private String province;

    @Column(name = "EMI_PAS_CER")
    private String passwordCerticate;

    @Column(name = "EMI_NAM_CER")
    private String nameCerticate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMI_FK_COD_CUE")
    // @JsonBackReference
    private Count count;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sender")
    @JsonManagedReference
    private List<Subsidiary> subsidiarys;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sender")
    private List<DetailsPerson> detailsPersons;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "EMA_FK_COD_EMI")
    private List<EmailSender> emailSenders;


    public Sender() {
        super();
        initData();
    }
    public Sender(Long ide) {
        super();
        this.ide = ide;
        initData();
    }

    public Sender(Long ide, String ruc, String socialReason, String commercialName, String matrixAddress,
                  String specialContributor, String retentionAgent, QuestionEnum accountancy, TypeSenderEnum typeSender, String logo,
                  TypeEnvironmentEnum typeEnvironment, TypeEmissionEnum typeEmission, boolean rimpe, ProvinceEnum province) {
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
        this.logo = logo;
        this.typeEnvironment = typeEnvironment.getCode();
        this.typeEmission = typeEmission.getCode();
        this.rimpe = rimpe;
        this.province = province.getCode();

        initData();
    }

    private void initData() {

        subsidiarys = new ArrayList<>(0);

        employees = new ArrayList<>(0);

        detailsPersons = new ArrayList<>(0);

        emailSenders = new ArrayList<>(0);

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

    public void setTypeEnvironment(TypeEnvironmentEnum typeEnvironment) {

        if (typeEnvironment != null) {
            this.typeEnvironment = typeEnvironment.getCode();
        } else
            this.typeEnvironment = null;

    }

    public String getTypeEmission() {
        return typeEmission;
    }

    public void setTypeEmission(TypeEmissionEnum typeEmission) {
        this.typeEmission = typeEmission.getCode();
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

    public void setProvince(ProvinceEnum province) {
        if (province != null) {
            this.province = province.getCode();
        } else
            this.province = null;

    }

    public void setCount(Count count) {
        this.count = count;

        // count.setSender(this);
    }

    public Count getCount() {
        return count;
    }

    public List<Subsidiary> getSubsidiarys() {
        return subsidiarys;
    }

    public void addSubsidiary(Subsidiary subsidiary) {

        subsidiary.setSender(this);
        this.subsidiarys.add(subsidiary);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {

        this.addEmployee(employee);
    }

    public List<DetailsPerson> getDetailsPersons() {
        return detailsPersons;
    }

    public void addDetailsPerson(DetailsPerson detailsPerson) {

        this.detailsPersons.add(detailsPerson);
    }


    public List<EmailSender> getEmailSenders() {
        return emailSenders;
    }

    public void setEmailSenders(List<EmailSender> emailSenders) {
        this.emailSenders = emailSenders;
    }


    public void addEmailSenders(EmailSender emailSender) {
        this.emailSenders.add(emailSender);
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

    @Override
    public String toString() {
        return "Sender [ide=" + ide + ", ruc=" + ruc + ", socialReason=" + socialReason + ", commercialName="
                + commercialName + ", matrixAddress=" + matrixAddress + ", specialContributor=" + specialContributor
                + "retentionAgent = " + retentionAgent + ", accountancy=" + accountancy + ", typeSender=" + typeSender + ", logo=" + logo
                + ", typeEnvironment=" + typeEnvironment + ", typeEmission=" + typeEmission + ", rimpe=" + rimpe
                + ", province=" + province + "]";
    }

}
