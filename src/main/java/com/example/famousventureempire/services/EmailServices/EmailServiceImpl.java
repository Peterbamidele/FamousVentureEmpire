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
    @Autowired
    private JavaMailSender javaMailSender;




    @Override
    public void sendMail(List<Product> productList,String PhoneNumber) throws MessagingException {
      String customerOrder ="\n";


      for(Product product:productList){

          customerOrder += "[[Quantity]]," + "[[Price]]," + "[[Description]],";
         customerOrder= customerOrder.replace("[[Quantity]]",String.valueOf(product.getProductQuantity()));
         customerOrder= customerOrder.replace("[[Price]]",String.valueOf(product.getPrice()));
          customerOrder=customerOrder.replace("[[Description]]",String.valueOf(product.getDescription()));
          customerOrder+="\n";
      }
        String mailSubject = "Customer Order ";
        String sender = "e-commercewebsite@gmail.com";
        String template = "User phone number: [[name]],<br>"
                + " Kindly find Customer Order below"
                +  "[[Order]]"
                + "And reach out to them"

                + "Thank you."
                + " order notification"
                + " FamousVentures";



        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessage = new MimeMessageHelper(message);
        mimeMessage.setFrom(sender);
        mimeMessage.setSubject(mailSubject);
        mimeMessage.setTo("onabanjotitus01@gmail.com");

        template = template.replace("[[name]]", PhoneNumber);
        template=template.replace("[[Order]]",customerOrder);


        mimeMessage.setText(template);
        log.info(String.valueOf(message));
        log.info("The message sent is -->{}",message);
        log.info("The Order sent is -->{}",customerOrder);

        javaMailSender.send(message);


    }
}
