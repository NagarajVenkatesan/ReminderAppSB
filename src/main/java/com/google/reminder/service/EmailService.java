package com.google.reminder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.reminder.dto.EmailDetails;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public boolean sendSimpleMail(EmailDetails details) {

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(sender);
			helper.setTo(details.getRecipient());
			helper.setSubject(details.getSubject());
			helper.setText(details.getMsgBody(), true);
			javaMailSender.send(message);
			return true;
		}

		catch (Exception e) {
			return false;
		}
	}
}
