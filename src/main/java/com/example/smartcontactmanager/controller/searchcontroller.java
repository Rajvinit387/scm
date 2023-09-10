package com.example.smartcontactmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartcontactmanager.dao.contactRepository;
import com.example.smartcontactmanager.dao.userRepository;
import com.example.smartcontactmanager.entities.User;
import com.example.smartcontactmanager.entities.contact;

@RestController
public class searchcontroller {
	
	@Autowired
	private userRepository userRepository;
	@Autowired
	private contactRepository contactRepository;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,   Principal principal)
	{
		
		String prString= principal.getName();
		User user= this.userRepository.getUserbyUserName(prString);
	List<contact> contacts =	this.contactRepository.findByCnameContainingAndUser(query, user);
		return ResponseEntity.ok(contacts);
		
	}

}
