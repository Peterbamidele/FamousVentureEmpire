package com.example.famousventureempire.web.controllers;

import com.example.famousventureempire.data.model.ProductsDto;
import com.example.famousventureempire.services.ProductServices;
import com.example.famousventureempire.web.exceptions.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
//@RestController
@RequestMapping("")
@Controller
public class LandingPageController {
    @Autowired
    ProductServices productServices;
    @GetMapping("")
    public String landingPage(){
        return "index";
    }

    @GetMapping("/create")
    public String getPostForm(Model model){
        model.addAttribute("productDto", new ProductsDto());
        return "create";
    }
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute @Valid ProductsDto productsDto, BindingResult result, Model model){
        log.info("Post dto received-->{}",productsDto);
        if(result.hasErrors()){
            return "create";
        }
        try{
            productServices.addProduct(productsDto);
        } catch (ProductException e) {
            log.info("Exception occurred -->{}",e.getMessage());
        }catch(DataIntegrityViolationException dx){
            model.addAttribute("error",dx.getMessage());
            model.addAttribute("errorMessage",dx.getMessage());
            model.addAttribute("productsDto",productsDto);
            return "create";
        }
        return "redirect:/";
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
    public ProductsDto findAProductById(@PathVariable("id") Integer id){
     ProductsDto productsDto= new ProductsDto();
        try{
          productsDto=productServices.findProductById(id);


      }  catch (ProductException productException){
          System.err.println(productException.getMessage());
      }
        return productsDto;

    }
    @GetMapping("/findAProductByNameContaining/{id}")
    public List<ProductsDto> findAProductByNameContaining(@PathVariable("id") String id){
        List<ProductsDto>productsDto=new ArrayList<>();
        try{
            productsDto=productServices.findProductsByNameContaining(id);
        }  catch (ProductException productException){
            System.err.println(productException.getMessage());
        }
        return productsDto;
    }


}
