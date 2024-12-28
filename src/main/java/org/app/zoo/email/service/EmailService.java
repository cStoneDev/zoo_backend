package org.app.zoo.email.service;

import org.app.zoo.config.errorHandling.MailSendException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;
    
    
    @Value("${spring.mail.username}")
    private String username;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(username);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException("Fallo al enviar el mensaje"); 
        }
        
    }
}