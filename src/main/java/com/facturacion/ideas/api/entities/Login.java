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
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "LOGINS")
public class Login implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOG_COD")
	private Long ide;

	@Column(name = "LOG_FEC_ING")
	private Date dateLogIn;

	@Column(name = "LOG_FEC_SAL")
	private Date dateLogOut;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOG_FK_COD_CUE")
	private Count count;
	
	public Login() {
		super();
	}

	public Login(Long ide, Date dateLogIn, Date dateLogOut) {
		super();
		this.ide = ide;
		this.dateLogIn = dateLogIn;
		this.dateLogOut = dateLogOut;
	}

	
	@PrePersist
	private void prePersistData() {
		
		dateLogIn =  new Date();
	}
	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public Date getDateLogIn() {
		return dateLogIn;
	}

	public void setDateLogIn(Date dateLogIn) {
		this.dateLogIn = dateLogIn;
	}

	public Date getDateLogOut() {
		return dateLogOut;
	}

	public void setDateLogOut(Date dateLogOut) {
		this.dateLogOut = dateLogOut;
	}
	
	
	public Count getCount() {
		return count;
	}
	public void setCount(Count count) {
		this.count = count;
	}
	


	@Override
	public String toString() {
		return "Login [ide=" + ide + ", dateLogIn=" + dateLogIn + ", dateLogOut=" + dateLogOut + "]";
	}
	
	

}
