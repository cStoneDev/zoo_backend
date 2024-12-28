package org.app.zoo.user.dto.out;

import jakarta.validation.constraints.Email;

public record UserOutputDTO(
    Long id,
    String username,
    @Email
    String email,
    String roleName
) {
} 
