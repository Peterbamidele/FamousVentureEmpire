package com.example.famousventureempire.data.model;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
@Data
public class ProductsDto {

    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private String category;
}
