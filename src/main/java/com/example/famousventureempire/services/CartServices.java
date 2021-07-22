package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Products;
import org.springframework.stereotype.Service;

@Service
public interface CartServices {
    void addProductToCart(Products product, int quantity);
}
