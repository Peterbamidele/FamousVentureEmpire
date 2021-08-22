package com.example.famousventureempire.data.model;

import lombok.Data;
import lombok.NonNull;


import javax.persistence.*;
import java.math.BigDecimal;
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;
    @Column
    private String image;
    @Column
    private String category;
    @Column
    private Integer productQuantity;
    @Column
    private BigDecimal grandTotal;

    public BigDecimal getGrandTotal(BigDecimal price,Integer productQuantity) {
        this.grandTotal = price.multiply(BigDecimal.valueOf(productQuantity));
        return grandTotal;
    }
}
