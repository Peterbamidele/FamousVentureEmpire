package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public interface CartServices {
    void addProductsToCart(String phoneNumber, Product product, Integer quantity);
    List<Cart> checkoutCart(String phoneNumber);
    Cart findCartsByUserNumber(String number);
    void saveCart(Cart cart);
}
