package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.model.ProductDto;
import com.example.famousventureempire.data.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        product.setDescription("bag");
//        product.setCategory("bags");
//        cartServices.addProductsToCart("1",product,2);
//
//        cartServices.addProductsToCart("1",product,6);
//
//        cartServices.addProductsToCart("1",product,26);
//        cartServices.addProductsToCart("2",product,2);
//
//        cartServices.addProductsToCart("2",product,6);
//
//        cartServices.addProductsToCart("2",product,26);
//        List<Cart> cartList= cartServices.checkoutCart("1");
//        log.info("The saved cart was -->{}",cartList);
//        cartServices.addProductsToCart("1",product,6);
//
//        cartServices.addProductsToCart("1",product,26);
//        cartList= cartServices.checkoutCart("1");
//        log.info("The saved cart was -->{}",cartList);
//        cartList= cartServices.checkoutCart("2");
//        log.info("The saved cart was -->{}",cartList);


    }

    @Test
    void checkoutCart() {
        product.setDescription("bag");
        product.setCategory("bags");
        cartServices.addProductsToCart("1",product,2);

//        List<Cart> cartList= cartServices.checkoutCart("1");
//        log.info("The saved cart was -->{}",cartList);
    }
}