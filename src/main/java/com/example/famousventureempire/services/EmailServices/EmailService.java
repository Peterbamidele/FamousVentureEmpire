package com.example.famousventureempire.services.EmailServices;

import com.example.famousventureempire.data.model.Product;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
@Service
public interface EmailService {
    void sendMail(List<Product> productList,String PhoneNumber) throws MessagingException;
}
