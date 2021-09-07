package com.example.famousventureempire.web.controllers;

import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.model.ProductDto;
import com.example.famousventureempire.services.CartServices;
import com.example.famousventureempire.services.ProductServices;
import com.example.famousventureempire.web.exceptions.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("")
@Controller
public class LandingPageController {
    @Autowired
    ProductServices productServices;
    @Autowired
    CartServices cartServices;

    List<Product> productDtoList=new ArrayList<>();
    @GetMapping("")
    public String landingPage(Model model,@ModelAttribute ProductDto productDto){
        model.addAttribute("productDto",productDto);
        return "index";
    }
    @GetMapping("/frags")
    public String frags(Model model,@ModelAttribute ProductDto productDto){
        model.addAttribute("productDto",productDto);
        return "fragments";
    }

    @GetMapping("/create")
    public String getPostForm(Model model,@ModelAttribute @Valid ProductDto productDto) {
        model.addAttribute("productDto", new ProductDto());
        return "create";
    }
    @RequestMapping(value = "/product",method = {RequestMethod.GET,RequestMethod.POST})
    public String productPage(Model model) {
        model.addAttribute("productList",productDtoList);
        model.addAttribute("productDto",new ProductDto());
        return "product";
    }

    @RequestMapping(value = "/product/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String productPage(Model model,@PathVariable String id) throws ProductException {
        log.info("The id* received is -->{}",id);
        productDtoList= productServices.findProductsByDescription(id);
        model.addAttribute("productDto",new ProductDto());
        return "redirect:/product";
    }
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto, Model model){

        log.info("Post dto received-->{}", productDto);

        try{
            productServices.addProduct(productDto);
            log.info("Post dto after save-->{}", productDto);
        } catch (ProductException e) {
            log.info("Exception occurred -->{}",e.getMessage());
        }
        return "create";
    }
    @PostMapping("/addProductToCart/{id}")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto,@PathVariable("id")Integer id) throws ProductException {
          Integer quantity=productDto.getProductQuantity();
          String phoneNumber = productDto.getPhoneNumber();
          productDto = productServices.findProductById(id);
          Product product = new Product();
          product.setDescription(productDto.getDescription());
          product.setPrice(productDto.getPrice());
          product.setCategory(productDto.getCategory());
          product.setName(productDto.getName());
          product.setImage(productDto.getImageReturned());
          product.setProductQuantity(quantity);
          product.setGrandTotal(product.getPrice().multiply(BigDecimal.valueOf(product.getProductQuantity())));
          log.info("The Dto Added To cart is -->{}",product);
          cartServices.addProductsToCart(phoneNumber, product, quantity);
        return "redirect:/";

    }
    @GetMapping("/Checkout/{phoneNumbers}")
    public String checkOut(@PathVariable String phoneNumbers) throws MessagingException {
        log.info("The number sent for checkout is -->{}",phoneNumbers);
        cartServices.checkoutCart(phoneNumbers);
      return "redirect:/";
    }

    @GetMapping("/removeFromCart/{description}/{price}/{productQuantity}/{phoneNumbers}")
    public String removeFromCart(@ModelAttribute @Valid ProductDto productDto, @PathVariable String description, @PathVariable BigDecimal price, @PathVariable Integer productQuantity, @PathVariable String phoneNumbers) {
        log.info("The received are found is -->{},{},{},{}", description, price, productQuantity, phoneNumbers);
        Product product = new Product();
        product.setProductQuantity(productQuantity);
        product.setDescription(description);
        product.setPrice(price);
        cartServices.deleteFromCart(product, phoneNumbers);
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
    @GetMapping("/findCart")
    public String findCartByUserNumber(Model model,@ModelAttribute @Valid ProductDto productDto){
        log.info("The phonenumber found is -->{}",productDto.getPhoneNumber());
        List<Product> productList=cartServices.findCartsAllByUserNumber(productDto.getPhoneNumber());
        log.info("The cart b4 stream found is -->{}",productList);
        log.info("The products found is -->{}",productList);
        model.addAttribute("productList",productList);
        model.addAttribute("phoneNumber",productDto.getPhoneNumber());
        model.addAttribute("productDto",productDto);
        return "cart";
    }

}
