package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findCartByUserNumber(String number);
    List<Cart>findAllByUserNumber(String number);
}
