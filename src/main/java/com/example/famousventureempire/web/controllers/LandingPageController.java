package com.example.famousventureempire.web.controllers;

import com.example.famousventureempire.data.model.ProductsDto;
import com.example.famousventureempire.services.ProductServices;
import com.example.famousventureempire.web.exceptions.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("")
public class LandingPageController {
    @Autowired
    ProductServices productServices;
    @GetMapping("")
    public String landingPage(){
        return "index";
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductsDto productsDto){
       try{
           log.info("One here");
           productServices.addProduct(productsDto);

       }
       catch (ProductException productException){
           System.err.println(productException.getMessage());
       }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void addProduct(@PathVariable ("id")Integer productId){
        try{ productServices.deleteProductsById(productId);
        }
        catch (ProductException productException){
            System.err.println(productException.getMessage());
        }
    }
    @GetMapping("/findAProduct/{id}")
    public Optional<ProductsDto> findAProductById(@PathVariable("id") Integer id){
        Optional<ProductsDto> productsDto=Optional.empty();
        try{
            log.info("the is is -->{}",id);
            log.info("The product found is-->{}",productServices.findProductById(id));
//         productsDto=productServices.findProductById(id);

      }  catch (ProductException productException){
          System.err.println(productException.getMessage());
      }
        return productsDto;

    }
    @GetMapping("/findAProductByNameContaining/{id}")
    public List<ProductsDto> findAProductByNameContaining(@PathVariable("id") String id){
        List<ProductsDto>productsDto=null;
        try{
            productsDto=productServices.findProductsByNameContaining(id);
        }  catch (ProductException productException){
            System.err.println(productException.getMessage());
        }
        return productsDto;
    }


}
