package com.example.stocks.notification;

import javax.mail.Session;

public interface EmailService {

     void  sendEmail(Session session, String toEmail, String subject, String body);
     void sendEmailToAdmin(String message);
}

