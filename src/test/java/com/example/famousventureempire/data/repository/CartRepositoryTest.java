package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.Cart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;
    
    Cart cart;
    @BeforeEach
    void setUp() {
        cart= new Cart();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findFromTheRepository(){
        cart.setCartId("123");
        cartRepository.save(cart);
        cartRepository.findById("123");
    }
}