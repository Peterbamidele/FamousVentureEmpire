package com.example.famousventureempire.data.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    @Column
    private String customerName;
    @Column
    private String deliveryAddress;
    @Column
    private String customerPhoneNumber;

    @OneToMany
    private List<Cart> cart;

}
