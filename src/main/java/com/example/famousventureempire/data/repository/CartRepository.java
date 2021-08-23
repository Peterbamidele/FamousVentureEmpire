package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart,Integer> {
    void  deleteByUserNumber(String id);
    Cart findCartByUserNumber(String number);
//    @Query(value = "SELECT g FROM Cart g JOIN FETCH g.productList gu",nativeQuery = true)
//    @Query(value = "SELECT * FROM Cart u WHERE u.status = 1",
//            nativeQuery = true)
    List<Cart>findAllByUserNumber(String number);
}
