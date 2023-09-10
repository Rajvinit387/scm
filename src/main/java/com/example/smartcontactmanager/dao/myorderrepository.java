package com.example.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smartcontactmanager.entities.MYORDER;

public interface myorderrepository extends JpaRepository<MYORDER, Long> {
	
	
	
	public MYORDER findByOrderId(String orderid);

}