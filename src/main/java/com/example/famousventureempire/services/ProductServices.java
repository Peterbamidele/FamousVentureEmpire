package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Products;
import com.example.famousventureempire.data.model.ProductsDto;
import com.example.famousventureempire.web.exceptions.CartException;
import com.example.famousventureempire.web.exceptions.ProductException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductServices {

    void deleteProductsById(Integer productsId) throws ProductException;
    void addProduct(ProductsDto productsDto) throws ProductException;
    Optional<ProductsDto>findProductById(Integer productsId) throws ProductException;
    List<ProductsDto> findProductsByNameContaining(String Name) throws ProductException;
}
