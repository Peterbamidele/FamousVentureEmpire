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
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class CartServicesImpl implements CartServices{

    @Autowired
    CartRepository cartRepository;
    @Autowired
    EntityManager entityManager;
    List<Product>productList=new Stack<>();
    Map<String,List<Product>>listMap= new HashMap<>();
    @Override
    public void addProductsToCart(String phoneNumber,Product product, Integer quantity) {
            log.info("The to save number is -->{}",phoneNumber);

            log.info("The product is -->{}",product);
            log.info("The previous is -->{}",listMap.get(phoneNumber));
            if(listMap.get(phoneNumber)!=null){
                this.productList=listMap.get(phoneNumber);
                this.productList.add(product);
                log.info("The previous is now -->{}",this.productList);
                listMap.put(phoneNumber,null);
                listMap.put(phoneNumber,this.productList);


                log.info("The to cart saved to the repository is-->{}",listMap.get(phoneNumber));
            }
            if(listMap.get(phoneNumber)==null){
                List<Product>productList= new ArrayList<>();
                product.setProductQuantity(quantity);
                product.setGrandTotal(product.getPrice().multiply(BigDecimal.valueOf(product.getProductQuantity())));
                productList.add(product);
                listMap.put(phoneNumber,productList);
                log.info("The to cart saved to the repository is-->{}",listMap.get(phoneNumber));
            }



    }

    @Override
    public List<Cart> checkoutCart(String id) {
        List<Cart>cartList=cartRepository.findAllByUserNumber(id);
        Cart optionalCart=cartRepository.findCartByUserNumber(id);
        optionalCart.setProduct(null);
        cartRepository.save(optionalCart);
        return cartList;

    }

    @Override
    public List<Product> findCartsAllByUserNumber(String number) {
        List<Product>productList=listMap.get(number);
        log.info("The BIG cart is-->{}",productList);
        return productList;
    }

    @Override
    public void deleteFromCart(Product product,String phoneNumber) {
        Product product2=listMap.get(phoneNumber).stream()
                .filter(product1 -> product1.getDescription().equals(product.getDescription()))
                .filter(product1 -> product1.getPrice().equals(product.getPrice()))
                .filter(product1 -> product1.getProductQuantity().equals(product.getProductQuantity())).findFirst().orElse(null);
        log.info("The product for delete is -->{}",product2);
        List<Product>productList1=listMap.get(phoneNumber);
        productList1.remove(product2);
        listMap.put(phoneNumber,productList1);

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
