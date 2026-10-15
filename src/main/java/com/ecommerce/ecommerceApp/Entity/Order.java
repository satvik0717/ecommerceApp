package com.ecommerce.ecommerceApp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orderTable")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="userId",nullable = false)
    private long userId;

    @Column(name ="productId" ,nullable = false)
    private long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "orderDate" ,nullable = false)
    private LocalDateTime orderDate;
}
