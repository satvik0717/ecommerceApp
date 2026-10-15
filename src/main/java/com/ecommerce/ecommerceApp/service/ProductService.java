package com.ecommerce.ecommerceApp.service;

import com.ecommerce.ecommerceApp.DTO.CategoryResponseDto;
import com.ecommerce.ecommerceApp.DTO.ProductRequestDto;
import com.ecommerce.ecommerceApp.DTO.ProductResponseDto;
import com.ecommerce.ecommerceApp.Entity.Category;
import com.ecommerce.ecommerceApp.Entity.Product;
import com.ecommerce.ecommerceApp.exception.ResourseNotFoundException;
import com.ecommerce.ecommerceApp.repository.CategoryRepo;
import com.ecommerce.ecommerceApp.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Category category = categoryRepo.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourseNotFoundException("Category not found with id: " + requestDto.getCategoryId()));

        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .productName(requestDto.getProductName())
                .productDescription(requestDto.getProductDescription())
                .productPrice(requestDto.getProductPrice())
                .productQuantity(requestDto.getProductQuantity())
                .category(category)
                .build();

        Product savedProduct = productRepo.save(product);
        return mapToDto(savedProduct);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Product not found with id: " + id));
        return mapToDto(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new ResourseNotFoundException("Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    private ProductResponseDto mapToDto(Product product) {
        CategoryResponseDto categoryDto = CategoryResponseDto.builder()
                .id(product.getCategory().getId())
                .categoryName(product.getCategory().getCategoryName())
                .build();

        return ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .productQuantity(product.getProductQuantity())
                .category(categoryDto)
                .build();
    }
}
