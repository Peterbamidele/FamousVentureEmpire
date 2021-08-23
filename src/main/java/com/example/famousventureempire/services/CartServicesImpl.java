package com.example.famousventureempire.services;

import com.example.famousventureempire.data.model.Cart;
import com.example.famousventureempire.data.model.Product;
import com.example.famousventureempire.data.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Slf4j
@Service
@Transactional
public class CartServicesImpl implements CartServices{

    @Autowired
    CartRepository cartRepository;
    Cart foundCart;
    Cart cart= new Cart();
    @Override
    public void addProductsToCart(String phoneNumber,Product product, Integer quantity) {
            log.info("The to save number is -->{}",phoneNumber);
            cart.setUserNumber(phoneNumber);
            product.setProductQuantity(quantity);
            product.setGrandTotal(product.getPrice().multiply(BigDecimal.valueOf(product.getProductQuantity())));
            cart.setProductList(Collections.singletonList(product));
            log.info("The to cart saved to the repository is-->{}",cart);
            cartRepository.save(cart);

    }

    @Override
    public List<Cart> checkoutCart(String id) {
        List<Cart>cartList=cartRepository.findAllByUserNumber(id);
        Cart optionalCart=cartRepository.findCartByUserNumber(id);
        optionalCart.setProductList(null);
        cartRepository.save(optionalCart);
        return cartList;

    }

    @Override
    public List<Cart> findCartsAllByUserNumber(String number) {
        List<Cart>cartList=cartRepository.findAllByUserNumber(number);
        log.info("The BIG cart is-->{}",cartList);
        return cartList;
    }

    @Override
    public Cart findCartsByUserNumber(String number) {
        return cartRepository.findCartByUserNumber(number);

    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

}
