package com.dayalbagh.epay.service;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl  implements EmailService{

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmailWithAttachment(String to, String subject, String body,String filepath) throws MessagingException, IOException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body);

		FileSystemResource file = new FileSystemResource(filepath);
		helper.addAttachment("fee_receipt.pdf", file);

		mailSender.send(message);
		}
	
	
	
}
