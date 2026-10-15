package com.ecommerce.ecommerceApp.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "Product ID is required")
    private long productId;

    @NotNull(message = "Quantity is mandatory")
    @Min( value = 1,message = "Quantity must be at least 1")
    private Integer quantity;
}
