package mx.edu.utez.huiclothes.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    public void sendMail(   ) throws MessagingException {
        try {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf8");

        helper.setTo("20223tn077@utez.edu.mx");
        helper.setSubject("asunto dslfsdfdjsl");

        Context context = new Context();

        String mensaje = "prueba para incrustar texto";
        context.setVariable("message",mensaje);
        String htmlContent = templateEngine.process("mailTemplate",context);

        helper.setText(htmlContent,true);

        mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("error al envíar el correo electrónico " + e.getMessage());
        }

    }
}
