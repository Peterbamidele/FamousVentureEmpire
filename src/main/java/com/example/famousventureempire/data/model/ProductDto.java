package com.example.famousventureempire.data.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
@Data
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private String category;
    private Integer productQuantity;
    private String phoneNumber;

}
