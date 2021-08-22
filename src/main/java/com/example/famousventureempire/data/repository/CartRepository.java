package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    void  deleteByUserNumber(String id);
    Cart findCartsByUserNumber(String number);
    List<Cart>findAllByUserNumber(String number);
}
