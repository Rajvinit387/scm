package com.example.smartcontactmanager.controller;

import com.example.smartcontactmanage.helper.message;
import com.example.smartcontactmanager.dao.userRepository;
import com.example.smartcontactmanager.entities.User;
import com.example.smartcontactmanager.services.emailservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class forgotcontroller {
	
	@Autowired
	private emailservice emailservice;
	
	@Autowired
	private userRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	Random random=new Random(1000);
	
	@RequestMapping("/forgot")
	public String openemailform()
	{
		return "forgot_email";
	}
	
	
	
	@PostMapping("/send-otp")
	public String sendotp(@RequestParam("email") String email, HttpSession session, Model model)
	{
		
		 int min=10000;
		 int max=999999;
		 int otp=(int)(Math.random()*(max-min+1)+min);
		
		//int otp= random.nextInt(999999);
		
		String msgString="<h1> OTP IS  -> "+otp+"<h1>";
		boolean flag= this.emailservice.sendemail("OTP from SCM", msgString, email);
		if(flag)
		{
			model.addAttribute("fotp",otp);
			model.addAttribute("email", email);
			return "accept_otp";
		}
		else {
			session.setAttribute("message", "Check your email id again");
			return "forgot_email";
			
		}
		
	}
	
	@PostMapping("/verify-otp")
	public String verifyotp( @RequestParam("otp") int otp, @RequestParam("otp1") int fotp, @RequestParam("email1") String emaiString, HttpSession session,Model model)
	{



		if(fotp==otp)
		{ 

		User user=	this.userRepository.getUserbyUserName(emaiString);
		
		if(user==null)
		{
			session.setAttribute("message", "check your email id..user does not exist with this email");
			return "forgot_email";
			
			
		}
		else {
			model.addAttribute("emailx",emaiString);
			return "password_change_form";
			
		}
		}
		else {
			session.setAttribute("message", "wrong otp");
			return "accept_otp";
		}
			
		
	}
	
	@PostMapping("/update-password")
	public String changepassString(@RequestParam("newpassword") String newpassword, @RequestParam("email") String emaString, HttpSession session)
	{

	User user=	this.userRepository.getUserbyUserName(emaString);
	user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
	this.userRepository.save(user);
	session.setAttribute("message",new message("password changed successfully", "alert-success"));
	return "login";

	}
	
	

}
