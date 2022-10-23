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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.TypeProductEnum;

@Entity
@Table(name = "PRODUCTOS")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRO_COD")
	private Long ide;

	@Column(name = "PRO_COD_PRI")
	private String codePrivate;

	@Column(name = "PRO_COD_AUX")
	private String codeAuxilar;

	@Column(name = "PRO_TIP")
	private String typeProductEnum;

	@Column(name = "PRO_NOM")
	private String name;

	@Column(name = "PRO_VAL_UNI")
	private double unitValue;

	@Column(name = "PRO_IVA")
	private String iva;

	@Column(name = "PRO_FEC_CRE")
	private Date dateCreate;

	@Column(name = "PRO_ICE")
	private String ice;

	@Column(name = "PRO_IRB")
	private String irbpnr;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRO_FK_COD_EST")
	private Subsidiary subsidiary;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "INF_FK_COD_PRO")
	private List<ProductInformation> productInformations;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	private List<TaxProduct> taxProducts;

	public Product() {
		super();
		initData();
	}

	public Product(Long ide, String codePrivate, String codeAuxilar, TypeProductEnum typeProductEnum, String name,
			double unitValue, String iva, String ice, String irbpnr) {
		super();
		this.ide = ide;
		this.codePrivate = codePrivate;
		this.codeAuxilar = codeAuxilar;
		this.typeProductEnum = typeProductEnum.getCode();
		this.name = name;
		this.unitValue = unitValue;
		this.iva = iva;
		this.ice = ice;
		this.irbpnr = irbpnr;
		initData();
	}

	@PrePersist
	public void prePersistData() {

		dateCreate = new Date();
	}

	private void initData() {

		productInformations = new ArrayList<>();
		taxProducts = new ArrayList<>();
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public String getCodePrivate() {
		return codePrivate;
	}

	public void setCodePrivate(String codePrivate) {
		this.codePrivate = codePrivate;
	}

	public String getCodeAuxilar() {
		return codeAuxilar;
	}

	public void setCodeAuxilar(String codeAuxilar) {
		this.codeAuxilar = codeAuxilar;
	}

	public String getTypeProductEnum() {
		return typeProductEnum;
	}

	public void setTypeProductEnum(TypeProductEnum typeProductEnum) {
		this.typeProductEnum = typeProductEnum.getCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getIce() {
		return ice;
	}

	public void setIce(String ice) {
		this.ice = ice;
	}

	public String getIrbpnr() {
		return irbpnr;
	}

	public void setIrbpnr(String irbpnr) {
		this.irbpnr = irbpnr;
	}

	public void setSubsidiary(Subsidiary subsidiary) {
		this.subsidiary = subsidiary;
	}

	public Subsidiary getSubsidiary() {
		return subsidiary;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setProductInformations(List<ProductInformation> productInformations) {
		this.productInformations = productInformations;
	}

	public List<ProductInformation> getProductInformations() {
		return productInformations;
	}

	public void addProductoInformation(ProductInformation productInformation) {
		this.productInformations.add(productInformation);
	}

	public List<TaxProduct> getTaxProducts() {
		return taxProducts;
	}
	
	public void setTaxProducts(List<TaxProduct> taxProducts) {
		this.taxProducts = taxProducts;
	}

	public void addTaxProduct(TaxProduct taxProduct) {
		this.taxProducts.add(taxProduct);
	}

	@Override
	public String toString() {
		return "Product [ide=" + ide + ", codePrivate=" + codePrivate + ", codeAuxilar=" + codeAuxilar
				+ ", typeProductEnum=" + typeProductEnum + ", name=" + name + ", unitValue=" + unitValue + ", iva="
				+ iva + ", ice=" + ice + ", irbpnr=" + irbpnr + "]";
	}

}
