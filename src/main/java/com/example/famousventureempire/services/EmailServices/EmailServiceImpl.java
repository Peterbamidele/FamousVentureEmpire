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


//    @Override
//    public void sendMail(List<Product> productList,String PhoneNumber) {
//        StringBuilder stringBuilder= new StringBuilder();
//        stringBuilder.append("The List Of Orders By Customer: ").append(PhoneNumber).append("is").append("\n");
//        stringBuilder.append("Product Name").append(" ").append("Product Quantity").append(" ").append("Product Price").append(" ").append("Total").append("\n");
//        for (Product product:productList) {
//            stringBuilder.append(product.getName()).append(" ").append(product.getProductQuantity()).append(" ").append(product.getPrice()).append(" ").append(product.getGrandTotal()).append("\n");
//        }
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo("onabanjotitus01@gmail.com", "onabanjotitus01@gmail.com");
//
//        msg.setSubject("Order List");
//        msg.setText(stringBuilder.toString());
//

//        log.info("You have checked out \n--->{}",stringBuilder.toString());
//
//    }




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
                + " Kindly find Customer Order below And reach out to them"
                +  "[[Order]]"
                + "\n\n"
                + "Thank you.\n"
                + " order notification"
                + " FamousVentures";



//        MimeMessage message = this.javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessage = new MimeMessageHelper(message);
//        mimeMessage.setFrom(sender);
//        mimeMessage.setSubject(mailSubject);
//        mimeMessage.setTo("onabanjotitus01@gmail.com");

        template = template.replace("[[name]]", PhoneNumber);
        template=template.replace("[[Order]]",customerOrder);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("damilolatitus61@gmail.com", "damilolatitus61@gmail.com");

        msg.setSubject("Order List");
        msg.setText(template);

//        mimeMessage.setText(template);
//        log.info(String.valueOf(message));
//        log.info("The message sent is -->{}",message);
//        log.info("The Order sent is -->{}",customerOrder);
//
//        javaMailSender.send(message);
                javaMailSender.send(msg);
    }
}
