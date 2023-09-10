package com.example.smartcontactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.sql.Delete;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.smartcontactmanage.helper.message;
import com.example.smartcontactmanager.dao.contactRepository;
import com.example.smartcontactmanager.dao.myorderrepository;
import com.example.smartcontactmanager.dao.userRepository;
import com.example.smartcontactmanager.entities.MYORDER;
import com.example.smartcontactmanager.entities.User;
import com.example.smartcontactmanager.entities.contact;

import com.razorpay.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class usercontroller {
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private userRepository userRepository;
	
	
	@Autowired
	private myorderrepository myorderrepository;
	
	
	@Autowired
     private contactRepository contactRepository;
	
	@ModelAttribute
	public void addcommondata(Model model, Principal principal)
	{

		String usernameString= principal.getName();
		System.out.println(usernameString);
	
	User user=	userRepository.getUserbyUserName(usernameString);
		model.addAttribute("userx",user);
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal)
	{
		
		
		return "/normal/user_dashboard";
	}
	
	
	@RequestMapping("/add-contact")
	public String openAddcontactform(Model model)
	{
		
		model.addAttribute("title", "this is add contact page");
		model.addAttribute("contact", new contact());
		
		return "/normal/add_contact";
	}
	
	@PostMapping("/process-contact")
	public String processcontactform(@ModelAttribute contact contact, @RequestParam("IMAGE") MultipartFile file, Principal principal, HttpSession session )
	{
		try {
		String nameString= principal.getName();
		User user= userRepository.getUserbyUserName(nameString);
		
		if(file.isEmpty())
		{
			System.out.println("file is empty");
			contact.setCimageurl("contact.png");
		}
		else {
			{
				contact.setCimageurl(file.getOriginalFilename());
				File savefile=  new ClassPathResource("static/image").getFile();
				
				
			Path path=	Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("image is uploaded");
				
				
				
			}
		}
		
		
		contact.setUser(user);
		user.getContacts().add(contact);
		this.userRepository.save(user);
		System.out.println("contacts added");
		System.out.println(contact);
		session.setAttribute("message",new message("Your contact has been added, you can add more contacts", "success"));
		
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("error msg:+"+e.getMessage());
			e.printStackTrace();
			session.setAttribute("message",new message("Something went wrong", "danger"));
		}
		
		return "/normal/add_contact";
	}
	
	@RequestMapping("/view-contacts/{page}")
	public String viewContacts(@PathVariable("page") Integer page, Model  m, Principal principal)
	{
		
		
		
		m.addAttribute("title","this is view contacts page");
		String usernameString= principal.getName();
		User user=	this.userRepository.getUserbyUserName(usernameString);
	Pageable pageable =	PageRequest.of(page, 3);
		
	Page <contact> contacts =	this.contactRepository.findContactsbyUserid(user.getId(), pageable);
	m.addAttribute("contacts", contacts);
	m.addAttribute("currentpage",page);
	m.addAttribute("totalpages",contacts.getTotalPages());
		
		return "/normal/view_contacts";
	}
	
	@RequestMapping("/{cId}/contact")
	public String showcontactdetail(@PathVariable("cId") Integer cid, Model m, Principal principal)
	{
		System.out.println("contact id  "+cid);
		
	Optional <contact> contactop=	this.contactRepository.findById(cid);
	
	contact contact= contactop.get();
String userString=	principal.getName();
User user= this.userRepository.getUserbyUserName(userString);


if(user.getId()==contact.getUser().getId())
{
	m.addAttribute("contact",contact);
	m.addAttribute("title",contact.getCname());
}
		return "normal/contact_detail";
	}
	
	
	
	
	@GetMapping("/delete/{cid}")
	public String deletecontact(@PathVariable("cid") Integer cid, Model model, HttpSession session, Principal principal)
	{
		Optional <contact> contactop=	this.contactRepository.findById(cid);
		contact contact= contactop.get();
		
		String userString=	principal.getName();
		User user= this.userRepository.getUserbyUserName(userString);
		user.getContacts().remove(contact);
		this.userRepository.save(user);
		
		
		session.setAttribute("message", new message("Contact deleted successfully", "success"));
		
		return "redirect:/user/view-contacts/0";
	}
	
	
   @PostMapping("/update-contact/{cid}")
	public String updateformcontact(@PathVariable("cid") Integer cid,   Model model)
	{
	   
	   model.addAttribute("title","this is update contact form");
	  contact contact= this.contactRepository.findById(cid).get();
	  model.addAttribute("contact", contact);
	   return "normal/update_form";
	}
   
   @PostMapping("/process-update")
   public String processupdatecontact(@ModelAttribute contact contact,@RequestParam("IMAGE") MultipartFile file, Model model, HttpSession session, Principal principal )
   {
	   try {
		 contact oldContact=  this.contactRepository.findById(contact.getCid()).get();
		   
		   if(!file.isEmpty())
		   {
			   File DELTEFILE=  new ClassPathResource("static/image").getFile();
			   File file1=new File(DELTEFILE,oldContact.getCimageurl());
			   file1.delete();
				
				
				
			   
			   
			   File savefile=  new ClassPathResource("static/image").getFile();
				
				
				Path path=	Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
					
					Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			   
			   contact.setCimageurl(file.getOriginalFilename());
		   }
		   else {
			   contact.setCimageurl(oldContact.getCimageurl());
		   }
		   
		User user=    this.userRepository.getUserbyUserName(principal.getName());
		contact.setUser(user);
		   this.contactRepository.save(contact);
		   session.setAttribute("message", new message("Contacts has been updated successfully", "success"));
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	   
	   
	   
	   
	   return "redirect:/user/"+contact.getCid()+"/contact";
   }
   
   @GetMapping("/profile")
   public String yourprofile(Model model)
   {
	   model.addAttribute("title","profile page");
	   
	   return "normal/profile";
   }
   
   @GetMapping("/settings")
   public String opensetting()
   {
	   return "normal/settings";
   }
	
   
   @PostMapping("/change-pass")
   public String changepass(@RequestParam("oldpass") String oldpass, @RequestParam("newpass") String newpass, Principal principal, HttpSession session)
   {
	 String getString=  principal.getName();
	 User user= this.userRepository.getUserbyUserName(getString);
	 if(this.bCryptPasswordEncoder.matches(oldpass, user.getPassword()))
	 {
	   user.setPassword(this.bCryptPasswordEncoder.encode(newpass));
	   this.userRepository.save(user);
	   session.setAttribute("message", new message("Password changed successfully", "success"));
	 }
	 else {
		  session.setAttribute("message", new message("Password was wrong..enter correct password", "danger"));
		  return "redirect:/user/settings"; 
	}
	   
	  return "redirect:/user/index"; 
   }
   
   @PostMapping("/create_order")
   @ResponseBody
   public String createorder(@RequestBody Map <String,Object> data, Principal principal) throws Exception
   {
	   System.out.println("order created");
	   String nameString= principal.getName();
	   User user=this.userRepository.getUserbyUserName(nameString);
	   System.out.println(data);
	int amount= Integer.parseInt( data.get("amount").toString())  ;
var client=	new RazorpayClient("rzp_test_l3HtCZnAksZm5W", "oubuiXm9YLeRr4lxI2FluXgv");



JSONObject object= new JSONObject();
object.put("amount", amount*100);
object.put("currency", "INR");
object.put("receipt", "txn_243533");

  Order order=  client.orders.create(object);
  System.out.println(order);
  MYORDER myorder= new MYORDER();
  myorder.setAmount(order.get("amount")+"");
  myorder.setOrderId(order.get("id"));
  myorder.setPaymentid(null);
  myorder.setReceipt(order.get("receipt"));
  myorder.setStatus("created");
  myorder.setUser(user);
  this.myorderrepository.save(myorder);
  
	  return order.toString();  
   }
   
   @PostMapping("/update_order")
   public ResponseEntity<?> updatepayment(@RequestBody  Map<String, Object> data)
   {
	   
	   
	MYORDER myorder=   this.myorderrepository.findByOrderId(data.get("order_id").toString());
	
	myorder.setPaymentid(data.get("payment_id").toString());
	myorder.setStatus(data.get("status").toString());
	
	this.myorderrepository.save(myorder);
	   
	   return ResponseEntity.ok(Map.of("msg","updated"));
   }
   
   
   
	
}

