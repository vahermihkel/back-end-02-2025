package ee.mihkel.veebipood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

//    @Autowired
//    JavaMailSender javaMailSender;
//
//    public void sendEmail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("hello@demomailtrap.co");
//        message.setSubject("Pealkiri");
//        message.setText("Sisu");
//        message.setTo("vahermihkel@gmail.com");
//        javaMailSender.send(message);
//    }
//
//    public void sendEmail(String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("hello@demomailtrap.co");
//        message.setSubject(subject);
//        message.setText(text);
//        message.setTo("vahermihkel@gmail.com");
//        javaMailSender.send(message);
//    }
}
