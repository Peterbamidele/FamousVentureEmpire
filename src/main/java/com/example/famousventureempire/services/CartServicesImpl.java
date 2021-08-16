package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CartServicesImpl implements CartServices{

    @Autowired
    CartRepository cartRepository;

    Cart cart= new Cart();
    @Override
    public void addProductsToCart(String phoneNumber,Product product, Integer quantity) {
        log.info("The to save number is -->{}",phoneNumber);
        Cart foundCart=cartRepository.findCartsByUserNumber(phoneNumber);
        log.info("The to cart found is-->{}",foundCart);
        if(foundCart!=null){
            List<Product>productList=foundCart.getProductList();
            productList.add(product);
            product.setProductQuantity(quantity);
            foundCart.setProductList(productList);
            cartRepository.deleteById(foundCart.getId());
            cartRepository.save(foundCart);
        }
        if(foundCart==null){
            cart.setUserNumber(phoneNumber);
            product.setProductQuantity(quantity);
            cart.setProductList(Collections.singletonList(product));
            log.info("The to cart saved to the repository is-->{}",cart);
            cartRepository.save(cart);
        }
    }

    @Override
    public List<Cart> checkoutCart(String id) {
        List<Cart>cartList=cartRepository.findAllByUserNumber(id);
        Cart optionalCart=cartRepository.findCartsByUserNumber(id);
        optionalCart.setProductList(null);
        cartRepository.save(optionalCart);
        return cartList;

    }

}
