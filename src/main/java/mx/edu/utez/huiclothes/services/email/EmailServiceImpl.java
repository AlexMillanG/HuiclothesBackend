package mx.edu.utez.huiclothes.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.user.dto.UserDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    public void sendMail(OrderBean orderBean, List<Map<String, Object>> productos, UserDto userDto) throws MessagingException {
        try {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf8");

        helper.setTo(userDto.getEmail());
        helper.setSubject("confirmación de tu pedido: "+ orderBean.getId());

        Context context = new Context();


        context.setVariable("nombre", userDto.getPersonName());
        context.setVariable("codigoPedido",orderBean.getId()) ;
        context.setVariable("productos",productos);
        context.setVariable("total",orderBean.getTotal()) ;
        context.setVariable("userEmail",userDto.getEmail());

        String htmlContent = templateEngine.process("mailTemplate",context);

        helper.setText(htmlContent,true);

        mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("error al envíar el correo electrónico " + e.getMessage());
        }

    }
}
