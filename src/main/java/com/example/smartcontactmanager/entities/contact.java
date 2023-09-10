package com.example.smartcontactmanager.entities;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACTS")
public class contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	
	private String cname;
	
	private String csecondname;
	
	private String cwork;
	
	
	private String cemail;
	
	private String cphone;
	
	private String cimageurl;
	



	@Column(length = 50000)
	private String cdesc;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCsecondname() {
		return csecondname;
	}
	public void setCsecondname(String csecondname) {
		this.csecondname = csecondname;
	}
	public String getCwork() {
		return cwork;
	}
	public void setCwork(String cwork) {
		this.cwork = cwork;
	}
	public String getCemail() {
		return cemail;
	}
	public void setCemail(String cemail) {
		this.cemail = cemail;
	}
	public String getCphone() {
		return cphone;
	}
	public void setCphone(String cphone) {
		this.cphone = cphone;
	}
	public String getCimageurl() {
		return cimageurl;
	}
	public void setCimageurl(String cimageurl) {
		this.cimageurl = cimageurl;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
	public contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	@Override
	public String toString() {
		return "contact [cid=" + cid + ", cname=" + cname + ", csecondname=" + csecondname + ", cwork=" + cwork
				+ ", cemail=" + cemail + ", cphone=" + cphone + ", cimageurl=" + cimageurl + ", cdesc=" + cdesc
				+ ", user=" + user + "]";
	}
	*/
	@Override
	public boolean equals(Object obj)
	{
		return this.cid==((contact)obj).getCid();
	}
	

}
