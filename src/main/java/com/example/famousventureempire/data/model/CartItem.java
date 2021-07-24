package com.example.famousventureempire.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private Product product;
    private int quantity = 1;
    private BigDecimal total;


    @NonNull
    private BigDecimal calculateCartItemPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    public void increaseCartItemQuantity(int quantity){
        this.quantity += quantity;
        total = calculateCartItemPrice();
    }

    public void decreaseCartItemQuantity(int quantity){
        this.quantity -= quantity;
        total = calculateCartItemPrice();
    }

}
