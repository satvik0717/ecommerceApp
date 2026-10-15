package com.ecommerce.ecommerceApp.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "productTable")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="product_name",nullable = false)
    private String productName;

    @Column(name = "productDescription")
    private String productDescription;

    @Column(name ="productPrice",nullable = false)
    private BigDecimal productPrice;

    @Column(name = "productQuantity",nullable = false)
    private Integer productQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="category_id",nullable = false)
    private Category category;

}
