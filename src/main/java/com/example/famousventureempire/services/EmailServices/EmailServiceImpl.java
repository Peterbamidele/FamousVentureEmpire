package com.example.famousventureempire.services.EmailServices;

import com.example.famousventureempire.data.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class EmailServiceImpl implements EmailService{
    @Override
    public void sendMail(List<Product> productList,String PhoneNumber) {
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("The List Of Orders By Customer: ").append(PhoneNumber).append("is").append("\n");
        stringBuilder.append("Product Name").append(" ").append("Product Quantity").append(" ").append("Product Price").append(" ").append("Total").append("\n");
        for (Product product:productList) {
            stringBuilder.append(product.getName()).append(" ").append(product.getProductQuantity()).append(" ").append(product.getPrice()).append(" ").append(product.getGrandTotal()).append("\n");
        }

        log.info("You have checked out \n--->{}",stringBuilder.toString());

    }
}
