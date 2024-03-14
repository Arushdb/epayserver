package com.dayalbagh.epay.service;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

public interface EmailService {
	
	public void sendEmailWithAttachment(String to, String subject, String body,String filepath) throws MessagingException, IOException;

}
