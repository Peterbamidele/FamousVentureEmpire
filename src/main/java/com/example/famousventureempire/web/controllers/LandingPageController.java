package com.example.famousventureempire.web.controllers;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.model.ProductDto;
import com.example.famousventureempire.services.CartServices;
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
@RequestMapping("")
@Controller
public class LandingPageController {
    @Autowired
    ProductServices productServices;
    @Autowired
    CartServices cartServices;
    Product product= new Product();
    @GetMapping("")
    public String landingPage(){
        return "index";
    }

    @RequestMapping(value = "/create",method = {RequestMethod.GET,RequestMethod.POST})
    public String getPostForm(Model model,@ModelAttribute @Valid ProductDto productDto) throws ProductException {
        log.info("The post recieved is -->{}",productDto);
        productServices.addProduct(productDto);
        model.addAttribute("productDto", new ProductDto());
        return "create";
    }
    @GetMapping("/product")
    public String productPage(Model model,String id) throws ProductException {
        id="oriamo";
        List<Product> productDtoList= productServices.findProductsByDescription(id);
        model.addAttribute("productList",productDtoList);
        return "product";
    }
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto, BindingResult result, Model model){

        log.info("Post dto received-->{}", productDto);

        try{
            productServices.addProduct(productDto);
            log.info("Post dto after save-->{}", productDto);
        } catch (ProductException e) {
            log.info("Exception occurred -->{}",e.getMessage());
        }catch(DataIntegrityViolationException dx){
            model.addAttribute("error",dx.getMessage());
            model.addAttribute("errorMessage",dx.getMessage());
            model.addAttribute("productsDto", productDto);
            return "create";
        }
        return "create";
    }
    @PostMapping("/addProductToCart")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto,String id){
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setName(productDto.getName());
        cartServices.addProductsToCart(id,product,productDto.getProductQuantity());
        return "redirect:/";
    }
    @GetMapping("/Checkout")
    public List<Cart> checkOut(String id){
      return cartServices.checkoutCart(id);
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
