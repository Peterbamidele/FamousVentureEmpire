package com.example.famousventureempire.services.EmailServices;

import com.example.famousventureempire.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }




    @Override
    public void sendMail(List<Product> productList,String PhoneNumber) throws MessagingException {
      String customerOrder ="\n";
        customerOrder += "Quantity      |" + "      Price|" + "        Description";
        customerOrder+="\n\n";
      for(Product product:productList){
          customerOrder += "[[Quantity]]        |" + "      [[Price]]|" + "     [[Description]].";
         customerOrder= customerOrder.replace("[[Quantity]]",String.valueOf(product.getProductQuantity()));
         customerOrder= customerOrder.replace("[[Price]]",String.valueOf(product.getGrandTotal()));
          customerOrder=customerOrder.replace("[[Description]]",String.valueOf(product.getDescription()));
          customerOrder+="\n\n";
      }
        String mailSubject = "Customer Order ";
        String sender = "e-commercewebsite@gmail.com";
        String template = "User phone number: [[name]],\n"
                + "Kindly find Customer Order below And reach out to them"
                +  "[[Order]]"
                + "\n\n"
                + "Thank you.\n"
                + " order notification"
                + " FamousVentures";
        template = template.replace("[[name]]", PhoneNumber);
        template=template.replace("[[Order]]",customerOrder);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("damilolatitus61@gmail.com", "damilolatitus61@gmail.com");
        msg.setSubject("Order List");
        msg.setText(template);
        javaMailSender.send(msg);
    }
}
