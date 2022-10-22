package com.facturacion.ideas.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DETALLE_PERSONAS")
public class DetailsPerson implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEP_COD")
	private Long ide;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEP_FK_COD_EMI")
	private Sender sender;

	
	// Podria ser EAGER
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEP_FK_COD_PER")
	private Person person;

	public DetailsPerson() {
		super();
	}

	public DetailsPerson(Long ide, Sender sender, Person person) {
		super();
		this.ide = ide;
		this.sender = sender;
		this.person = person;
	}

	public Long getIde() {
		return ide;
	}

	public void setIde(Long ide) {
		this.ide = ide;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "DetailsPerson [ide=" + ide + ", sender=" + sender.getIde() + ", person=" + person.getIde() + "]";
	}

}
