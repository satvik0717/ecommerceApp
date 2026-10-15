package com.ecommerce.ecommerceApp.service;

import com.ecommerce.ecommerceApp.DTO.OrderRequestDto;
import com.ecommerce.ecommerceApp.DTO.OrderResponseDto;
import com.ecommerce.ecommerceApp.Entity.Order;
import com.ecommerce.ecommerceApp.Entity.Product;
import com.ecommerce.ecommerceApp.Entity.User;
import com.ecommerce.ecommerceApp.exception.BadRequestException;
import com.ecommerce.ecommerceApp.exception.ResourseNotFoundException;
import com.ecommerce.ecommerceApp.repository.OrderRepo;
import com.ecommerce.ecommerceApp.repository.ProductRepo;
import com.ecommerce.ecommerceApp.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourseNotFoundException("Authenticated user not found"));

        Product product = productRepo.findById(requestDto.getProductId())
                .orElseThrow(() -> new ResourseNotFoundException("Product not found with id: " + requestDto.getProductId()));

        if (product.getProductQuantity() < requestDto.getQuantity()) {
            throw new BadRequestException("Insufficient stock! Available quantity: " + product.getProductQuantity());
        }
            product.setProductQuantity(product.getProductQuantity() - requestDto.getQuantity());
            productRepo.save(product);

            Order order = Order.builder()
                    .userId(user.getId()) // Securely bound to logged-in user
                    .productId(product.getId())
                    .quantity(requestDto.getQuantity())
                    .orderDate(LocalDateTime.now()) // Authoritative server timestamp[cite: 1]
                    .build();

            Order savedOrder = orderRepo.save(order);
            return mapToDto(savedOrder);
    }
        public List<OrderResponseDto> getAllOrders() {
            return orderRepo.findAll().stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        public OrderResponseDto getOrderById(Long id) {
            Order order = orderRepo.findById(id)
                    .orElseThrow(() -> new ResourseNotFoundException("Order not found with id: " + id));
            return mapToDto(order);
        }

        private OrderResponseDto mapToDto(Order order) {
            return OrderResponseDto.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .productId(order.getProductId())
                    .quantity(order.getQuantity())
                    .orderDate(order.getOrderDate())
                    .build();
        }

}
