package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.facturacion.ideas.api.enums.ProvinceEnum;
import com.facturacion.ideas.api.enums.QuestionEnum;
import com.facturacion.ideas.api.enums.TypeEmissionEnum;
import com.facturacion.ideas.api.enums.TypeEnvironmentEnum;
import com.facturacion.ideas.api.enums.TypeSenderEnum;


@Entity
@Table(name="emisores")
public class Sender implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="EMI_COD")
	private Long ide;
	
	@Column(name="EMI_RUC")
	private String ruc;
	
	@Column(name="EMI_RAZ_SOC")
	private String socialReason;
	
	@Column(name="EMI_NOM_COM")
	private String commercialName;
	
	@Column(name="EMI_DIR_MAT")
	private String matrixAddress;
	
	@Column(name="EMI_CON_ESP")
	private String specialContributor;
	
	@Column(name="EMI_OBL_CON")
	@Enumerated(EnumType.STRING)
	private QuestionEnum accountancy;
	
	@Column(name="EMI_TIP")
	@Enumerated(EnumType.STRING)
	private TypeSenderEnum typeSender;

	@Column(name="EMI_LOG")
	private String logo;
	
	@Column(name="EMI_TIP_AMB")
	@Enumerated(EnumType.STRING)
	private TypeEnvironmentEnum typeEnvironment;
	
	@Column(name="EMI_TIP_EMI")
	private TypeEmissionEnum typeEmission;
	
	@Column(name="EMI_REG_RIM")
	private boolean rimpe;
	
	@Column(name="EMI_PRO")
	private ProvinceEnum province;
	
	
	 
	
	
	
	

}
