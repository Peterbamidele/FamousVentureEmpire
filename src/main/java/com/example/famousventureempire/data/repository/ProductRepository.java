package com.example.famousventureempire.data.repository;

import com.example.famousventureempire.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<Product> findProductsByName(String name);
    List<Product> findProductsByNameContaining(String name);
    List<Product>findProductsByDescription(String name);

}
