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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("")
@Controller
public class LandingPageController {
    @Autowired
    ProductServices productServices;
    @Autowired
    CartServices cartServices;
    Product product= new Product();
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
    public String getPostForm(Model model,@ModelAttribute @Valid ProductDto productDto) throws ProductException {
        model.addAttribute("productDto", new ProductDto());
        return "create";
    }
    @RequestMapping(value = "/product",method = {RequestMethod.GET,RequestMethod.POST})
    public String productPage(Model model) throws ProductException {
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
    @PostMapping("/addProductToCart/{id}")
    public String addProduct(@ModelAttribute @Valid ProductDto productDto,@PathVariable("id")Integer id,Model model) throws ProductException {
          Integer quantity=productDto.getProductQuantity();
          String phoneNumber = productDto.getPhoneNumber();
          productDto = productServices.findProductById(id);
          product.setDescription(productDto.getDescription());
          product.setPrice(productDto.getPrice());
          product.setCategory(productDto.getCategory());
          product.setName(productDto.getName());
          product.setImage(productDto.getImageReturned());
          log.info("The Dto Added To cart is -->{}",productDto);
          cartServices.addProductsToCart(phoneNumber, product, quantity);
        return "redirect:/";

    }
    @GetMapping("/Checkout")
    public List<Cart> checkOut(String id){
      return cartServices.checkoutCart(id);
    }
    @PostMapping("/removeFromCart/{id}")
    public String removeFromCart(Model model, @ModelAttribute @Valid ProductDto productDto,@PathVariable("id") String id){

        Cart cart=cartServices.findCartsByUserNumber(id);
        log.info("The product found is -->{}",  cart.getProductList());
        log.info("The product found id -->{}",  productDto.getId());
        cart.getProductList().removeIf(product1 -> product1.getId().equals(productDto.getId()));
        cart.setProductList(cart.getProductList());
        log.info("The product found is -->{}",cart.getProductList());
        cartServices.saveCart(cart);

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
        List<Cart> cart=cartServices.findCartsAllByUserNumber(productDto.getPhoneNumber());
        log.info("The cart b4 stream found is -->{}",cart);
        List<Product> productList = cart                           // -> List<A>
                        .stream()                       // -> Stream<A>
                        .map(Cart::getProductList)             // -> Stream<List<String>>
                        .flatMap(Collection::stream)    // -> Stream<String>
                        .collect(Collectors.toList());
        log.info("The products found is -->{}",productList);
        model.addAttribute("productList",productList);
        model.addAttribute("phoneNumber",productDto.getPhoneNumber());
        model.addAttribute("productDto",productDto);

        return "cart";
    }

}
