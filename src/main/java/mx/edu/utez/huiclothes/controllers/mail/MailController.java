package mx.edu.utez.huiclothes.controllers.mail;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.services.email.EmailServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@AllArgsConstructor
public class MailController {

    private final EmailServices emailServices;

    @GetMapping("/")
    public void sendEmail (){
        emailServices.sendMail();
    }
}
