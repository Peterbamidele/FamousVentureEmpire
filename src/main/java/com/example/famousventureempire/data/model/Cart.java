package com.example.famousventureempire.data.model;

import com.example.famousventureempire.web.exceptions.CartException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cart {

    @Id
    private String cartId;
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Product> productList=new ArrayList<>();



}
