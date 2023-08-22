package com.dayalbagh.epay.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "certificate_semester")
public class Certificatesemester {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
			
	private String semester;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name ="certificateid" ,nullable =false,referencedColumnName = "id" )
	private Certificate certificate;
		
	public Certificatesemester() {
	
	}
	
	

	public String getSemester() {
		return semester;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	
		
	
	
	
	
	
	
	
	
	

}
