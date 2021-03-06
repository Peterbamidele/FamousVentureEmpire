package com.example.famousventureempire.data.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private String imageReturned;
    private String category;
    private Integer productQuantity;
    private String phoneNumber;
    private String oriamo="Oriamo";
    private String Lavelier="Lavelier";
    private String hp="Hp";
    private String powerBank="Power Bank";
    private String carwireless="Car wireless";
    private String smartwatch ="smartwatch";
    private String  xtreme =" xtreme";
    private String JBLWireless ="JBLWireless";


}
