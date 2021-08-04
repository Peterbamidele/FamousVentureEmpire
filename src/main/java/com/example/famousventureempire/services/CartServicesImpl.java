package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class CartServicesImpl implements CartServices{

    @Autowired
    CartRepository cartRepository;

    Cart cart= new Cart();
    @Override
    public void addProductsToCart(String id, Product product, Integer quantity) {
        log.info("The to save id is -->{}",id);
        boolean isPresent=cartRepository.findById(id).isPresent();
        if(isPresent){
            product.setProductQuantity(quantity);
            Optional<Cart>optionalCart=cartRepository.findById(id);
            optionalCart.get().getProductList().add(product);
            cartRepository.save(optionalCart.get());
        }
        if(!isPresent){
            cart.setCartId(id);
            product.setProductQuantity(quantity);
            cart.getProductList().add(product);
            cartRepository.save(cart);
        }
    }

    @Override
    public List<Cart> checkoutCart(String id) {
        List<Cart>cartList=cartRepository.findAllById(Collections.singleton(id));

        Cart optionalCart=cartRepository.findById(id).get();
        optionalCart.setProductList(null);
        cartRepository.save(optionalCart);

        return cartList;

    }

}
