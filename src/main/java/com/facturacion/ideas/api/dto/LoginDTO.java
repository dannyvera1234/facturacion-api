package com.facturacion.ideas.api.dto;

import java.io.Serializable;
public class LoginDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private Long ide;
	private String dateLogIn;
	private String dateLogOut;
	
	
	
	public LoginDTO() {
		super();
	}



	public LoginDTO(Long ide, String dateLogIn, String dateLogOut) {
		super();
		this.ide = ide;
		this.dateLogIn = dateLogIn;
		this.dateLogOut = dateLogOut;
	}



	public Long getIde() {
		return ide;
	}



	public void setIde(Long ide) {
		this.ide = ide;
	}



	public String getDateLogIn() {
		return dateLogIn;
	}



	public void setDateLogIn(String dateLogIn) {
		this.dateLogIn = dateLogIn;
	}



	public String getDateLogOut() {
		return dateLogOut;
	}



	public void setDateLogOut(String dateLogOut) {
		this.dateLogOut = dateLogOut;
	}



	@Override
	public String toString() {
		return "LoginDTO [ide=" + ide + ", dateLogIn=" + dateLogIn + ", dateLogOut=" + dateLogOut + "]";
	} 
	
	
	

}
