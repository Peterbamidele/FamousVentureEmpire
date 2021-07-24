package com.example.famousventureempire.web.controllers;

import com.example.famousventureempire.data.model.ProductDto;
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
        model.addAttribute("productDto", new ProductDto());
        return "create";
    }
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto, BindingResult result, Model model){
        log.info("Post dto received-->{}", productDto);
        if(result.hasErrors()){
            return "create";
        }
        try{
            productServices.addProduct(productDto);
        } catch (ProductException e) {
            log.info("Exception occurred -->{}",e.getMessage());
        }catch(DataIntegrityViolationException dx){
            model.addAttribute("error",dx.getMessage());
            model.addAttribute("errorMessage",dx.getMessage());
            model.addAttribute("productsDto", productDto);
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
    public ProductDto findAProductById(@PathVariable("id") Integer id){
     ProductDto productDto = new ProductDto();
        try{
          productDto =productServices.findProductById(id);


      }  catch (ProductException productException){
          System.err.println(productException.getMessage());
      }
        return productDto;

    }
    @GetMapping("/findAProductByNameContaining/{id}")
    public List<ProductDto> findAProductByNameContaining(@PathVariable("id") String id){
        List<ProductDto> productDto =new ArrayList<>();
        try{
            productDto =productServices.findProductsByNameContaining(id);
        }  catch (ProductException productException){
            System.err.println(productException.getMessage());
        }
        return productDto;
    }


}
