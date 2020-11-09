package com.zelinskiyrk.blog.base.service;

import com.zelinskiyrk.blog.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Value("spring.mail.username")
    private String fromEmail;

    public void sendEmail(String title, String body, String to){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailRegistration(String to){
        sendEmail("Вы зарегистрировались", "Вы успешно зарегистрировались", to);
    }

    public void sendEmailAlert(String to){
        String ip = AuthService.getCurrentRequest().getRemoteAddr();
        sendEmail("Внимание! Неудачная попытка входа", "Неудачная попытка входа с IP" + ip, to);
    }
}
