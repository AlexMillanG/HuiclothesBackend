package mx.edu.utez.huiclothes.controllers.mail;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.services.email.EmailServiceImpl;
import mx.edu.utez.huiclothes.services.email.EmailServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@AllArgsConstructor
@CrossOrigin({"*"})
public class MailController {

    private final EmailServices emailServices;

    private final EmailServiceImpl emailServiceDos;

    @GetMapping("/")
    public void sendEmail () throws MessagingException {
        emailServiceDos.sendMail();
    }
}
