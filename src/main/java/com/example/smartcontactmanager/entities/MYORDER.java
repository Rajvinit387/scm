package com.example.smartcontactmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Orders")
public class MYORDER {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private String orderId;
	
	private String amount;
	
	
	private String receipt;
	
	
	private String status;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getReceipt() {
		return receipt;
	}


	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getPaymentid() {
		return paymentid;
	}


	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}


	@ManyToOne
	private User user;
	
	
	private String paymentid;
	
	

}
