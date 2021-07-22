package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,String> {
    Optional<Products> findProductsByName(String name);
    List<Products> findProductsByNameContaining(String name);

}
