package com.example.smartcontactmanager.services;

import org.springframework.stereotype.Service;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;

import java.io.File;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.ArrayBuilders.BooleanBuilder;
@Service
public class emailservice {
	public boolean sendemail(String subject, String message, String to)
	{
	boolean flagBoolean=false;
	String from="rajvinit387@gmail.com";
	
	
	String host="smtp.gmail.com";
Properties properties=	System.getProperties();

properties.put("mail.smtp.host", host);
properties.put("mail.smtp.port", "465");
properties.put("mail.smtp.ssl.enable", "true");
properties.put("mail.smtp.auth", "true");


Session session=	Session.getInstance(properties, new Authenticator(){

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication("rajvinit387@gmail.com","lgiuvichfbznjsti");
	}
	
});


session.setDebug(true);
MimeMessage m=   new MimeMessage(session);

try
{
m.setFrom(from);
m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

m.setSubject(subject);
//m.setText(message);
m.setContent(message,"text/html");


Transport.send(m);
System.out.println("email sent");
flagBoolean= true;


}catch(Exception e)
{

e.printStackTrace();
}

return flagBoolean;
}

}

