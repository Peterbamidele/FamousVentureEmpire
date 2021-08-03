package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class CartServicesImplTest {

    @Autowired
    CartServices cartServices;
    @Autowired
    CartRepository cartRepository;
    Product product;
    @BeforeEach
    void setUp() {
    product= new Product();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addProductsToCart() {
        product.setDescription("bag");
        product.setCategory("bags");
       cartServices.addProductsToCart("1",product,2);

        cartServices.addProductsToCart("1",product,6);

        cartServices.addProductsToCart("1",product,26);
        List<Cart> cartList= cartServices.checkoutCart("1");
        log.info("The saved cart was -->{}",cartList);
        cartServices.addProductsToCart("1",product,6);

        cartServices.addProductsToCart("1",product,26);
        cartList= cartServices.checkoutCart("1");
        log.info("The saved cart was -->{}",cartList);


    }

    @Test
    void checkoutCart() {
       List<Cart> cartList= cartServices.checkoutCart("1234");
        log.info("The saved cart was -->{}",cartList);
    }
}