package com.example.smartcontactmanager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.smartcontactmanager.entities.User;
import com.example.smartcontactmanager.entities.contact;

public interface contactRepository extends JpaRepository<contact, Integer> {
	
	@Query(value="select * from contacts where user_id = ?1 ",nativeQuery = true)
	public  Page<contact> findContactsbyUserid(int  userid, Pageable pageable);
	
	
	
	public List<contact> findByCnameContainingAndUser(String keyword, User user);
	
	
	}


