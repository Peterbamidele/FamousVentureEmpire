package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface CartServices {
    void addProductToCart(Product product, int quantity);
}
