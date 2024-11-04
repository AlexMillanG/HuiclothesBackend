package mx.edu.utez.huiclothes.services.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor

public class EmailServices {

    private JavaMailSender mailSender;

    public void sendMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("alexmillanguillen@gmail.com");
        message.setSubject("prueba correos");
        message.setText("jhjhkgkhjgkhjghgouhfouhghjgkng");
        message.setFrom("20223tn072@utez.edu.mx");

        mailSender.send(message);
        System.err.println("mail send");
    }
}
