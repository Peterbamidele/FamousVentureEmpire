package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;



@Slf4j
@Service
public class CartServicesImpl implements CartServices{
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    CartRepository cartRepository;

    Cart cart= new Cart();
    @Override
    public void addProductsToCart(String phoneNumber,Product product, Integer quantity) {
        log.info("The to save number is -->{}",phoneNumber);
        Cart foundCart=cartRepository.findCartsByUserNumber(phoneNumber);
        log.info("The to cart found is-->{}",foundCart);
        if(foundCart!=null){
            product.setProductQuantity(quantity);
            List<Product>productList=foundCart.getProductList();
            productList.add(product);
            foundCart.setProductList(productList);
            log.info("The to cart found is-->{}",foundCart);
            cartRepository.save(foundCart);
        }
        if(foundCart==null){
            cart.setUserNumber(phoneNumber);
            product.setProductQuantity(quantity);
            product.setGrandTotal(product.getPrice().multiply(BigDecimal.valueOf(product.getProductQuantity())));
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

    @Override
    public Cart findCartsByUserNumber(String number) {
        return cartRepository.findCartsByUserNumber(number);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

}
