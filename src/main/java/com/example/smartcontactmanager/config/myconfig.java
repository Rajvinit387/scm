package com.example.smartcontactmanager.config;

import org.apache.catalina.startup.WebAnnotationSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class myconfig   {
	
	
	
	@Bean
	public UserDetailsService getuserdetailsservice()
	{
		return new userdetailserviceimpl();
	}
	
	
	
@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

@Bean
public AuthenticationProvider AuthenticationProvider1()
{
	 DaoAuthenticationProvider da= new DaoAuthenticationProvider();
	da.setUserDetailsService(this.getuserdetailsservice());
	da.setPasswordEncoder(passwordEncoder());
	return da;
}

/*
@Bean	
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)	throws Exception
{
	return authenticationConfiguration.getAuthenticationManager();
}*/


@SuppressWarnings("deprecation")
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
{
http.authorizeHttpRequests().requestMatchers("/admin**").hasRole("ADMIN")
.requestMatchers("/user/**").hasRole("USER").requestMatchers("/search/**").hasRole("USER").requestMatchers("/**").permitAll()
.and().formLogin().loginPage("/login1").
loginProcessingUrl("/dologin").
and().csrf().disable();
http.formLogin().defaultSuccessUrl("/user/index",true);
return http.build();


}

}
