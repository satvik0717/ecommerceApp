package com.ecommerce.ecommerceApp.repository;

import com.ecommerce.ecommerceApp.Entity.Product;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long id);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}
