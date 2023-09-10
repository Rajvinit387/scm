package com.example.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.smartcontactmanager.dao.userRepository;
import com.example.smartcontactmanager.entities.User;

public class userdetailserviceimpl implements UserDetailsService {

	@Autowired
	private userRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User usrUser= userRepository.getUserbyUserName(username);
		if(usrUser==null)
			
			
			throw new UsernameNotFoundException("user doesnt exists");
		
		customuserdetails customuserdetails= new customuserdetails(usrUser);
		return customuserdetails;
		
		
				
				
	}

	
	
}
