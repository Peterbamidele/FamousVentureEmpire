package com.example.famousventureempire.data.model;

import lombok.Data;


import javax.persistence.*;
import java.math.BigDecimal;
@Data
@Entity
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column
    private String name;
    @Column
    private String Description;
    @Column
    private BigDecimal price;
    @Column
    private String image;
    @Column
    private String category;


}
