package com.example.smartcontactmanager.controller;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.smartcontactmanage.helper.message;
import com.example.smartcontactmanager.dao.userRepository;
import com.example.smartcontactmanager.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Homecontroller {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	private userRepository userRepository;
	
	
@GetMapping("/")	
public String home(Model model )
{
	model.addAttribute("title","this is home page");
	return "home";
}

@GetMapping("/about")	
public String about(Model model )
{
	model.addAttribute("title","this is about page");
	return "about";
}

@GetMapping("/signup")	
public String Signup(Model model )
{

	model.addAttribute("object",new User());
	model.addAttribute("title","this is signup page");
	return "signup";
}


@PostMapping("/process")	
public String process(@Valid @ModelAttribute("object") User user, BindingResult reult, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement , Model model, HttpSession session )
{

		
	
	try {
		
		

		if(reult.hasErrors())
		{
			System.out.println("error"+reult.toString());
//			model.addAttribute("object", user);
			return "signup";
		}
	
		if(!agreement)
		{
			System.out.println("you have not agreed to terms and conditions");
			throw new Exception("you have not agreed to terms and conditions");
		}
		user.setEnabled(true);
		user.setRole("ROLE_USER");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		if(user.getImageurl() ==null)
			user.setImageurl("default.png");
		
		System.out.println("agreement"+agreement);
		System.out.println(user);
		User user2  = this.userRepository.save(user);
		
		model.addAttribute("object",user2);
		session.setAttribute("message",new message("User registered successfully...","alert-success"));
	return "signup";
		
	} catch (Exception e) {
		// TODO: handle exception
		session.setAttribute("message",new message("Unable to register.."+e.getMessage(),"alert-danger"));
		e.printStackTrace();
	}
	return "signup";
	}
 
@GetMapping("/login1")
public String  loginString(Model model)
{
	return "login";
}





}
