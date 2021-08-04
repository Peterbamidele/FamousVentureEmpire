package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.ProductDto;
import com.example.famousventureempire.web.exceptions.ProductException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductServices {

    void deleteProductsById(Integer productsId) throws ProductException;
    void addProduct(ProductDto productDto) throws ProductException;
    ProductDto findProductById(Integer productsId) throws ProductException;
    List<ProductDto> findProductsByNameContaining(String Name) throws ProductException;
    List<ProductDto> findProductsByDescription(String name) throws ProductException;
}
