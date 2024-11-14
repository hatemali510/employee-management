package com.stc.employee_management.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${registration.welcome.mail.subject}")
    private String welcomeMailSubject;

    @Value("${registration.sender.email}")
    private String registrationSenderMail;

    @Value("${registration.welcome.mail.text}")
    private String welcomeMailText;


    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /*
    running this method in async to non block the registration process and send mail in the background
     */
    @Async
    public void sendWelcomeRegistrationMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(welcomeMailSubject);
        message.setText(welcomeMailText);
        message.setFrom(registrationSenderMail);
        mailSender.send( message);
        log.info("registration mail sent successfully ");
    }





}