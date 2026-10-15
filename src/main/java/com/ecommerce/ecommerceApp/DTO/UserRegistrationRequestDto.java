package com.ecommerce.ecommerceApp.DTO;

import com.ecommerce.ecommerceApp.Entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequestDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3,max = 30 ,message = "Username must be between 3 to 30 character")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8,message = "Password must be atleast 8 characters")
    private String password;

    private Role role;
}
