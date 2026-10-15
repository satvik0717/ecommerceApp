package com.ecommerce.ecommerceApp.service;

import com.ecommerce.ecommerceApp.DTO.CategoryRequestDto;
import com.ecommerce.ecommerceApp.DTO.CategoryResponseDto;
import com.ecommerce.ecommerceApp.Entity.Category;
import com.ecommerce.ecommerceApp.exception.BadRequestException;
import com.ecommerce.ecommerceApp.exception.ResourseNotFoundException;
import com.ecommerce.ecommerceApp.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        if (categoryRepo.existsByCategoryName(requestDto.getCategoryName())) {
            throw new BadRequestException("Category already exists with name: " + requestDto.getCategoryName());
        }
        Category category = Category.builder()
                .categoryName(requestDto.getCategoryName())
                .build();
        Category saved = categoryRepo.save(category);
        return mapToDto(saved);
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Category not found with id: " + id));
        return mapToDto(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepo.existsById(id)) {
            throw new ResourseNotFoundException("Category not found with id: " + id);
        }
        categoryRepo.deleteById(id);
    }
    private CategoryResponseDto mapToDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
