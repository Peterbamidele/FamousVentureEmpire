package com.example.famousventureempire.data.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.math.BigDecimal;
@Data
public class ProductsDto {

    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private String category;
}
