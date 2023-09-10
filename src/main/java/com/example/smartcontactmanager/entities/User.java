package com.example.smartcontactmanager.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@Column(unique = true)
	@NotBlank(message ="must not be blank")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "invalid mail")
	private String email;
	
	@NotBlank(message = "must not be empty")
	private String password;
	
	@NotBlank(message = "must not be empty")
	@Size(min =3, max=12, message = "must be between these")
	private String name;
	
	
	
	private String role;
	
	private boolean enabled;
	private String imageurl;
	
	@Column(length = 500)

	private String about;
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	
	private List<contact> contacts= new ArrayList<>();
	
	
	public List<contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<contact> contacts) {
		this.contacts = contacts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", role=" + role
				+ ", enabled=" + enabled + ", imageurl=" + imageurl + ", about=" + about + ", contacts=" + contacts
				+ "]";
	}
	
	

	
	

}
