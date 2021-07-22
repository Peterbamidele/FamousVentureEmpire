package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CartRepository extends JpaRepository<CartItem,String> {

}
