package org.app.zoo.user.dto.in;

import jakarta.validation.constraints.Email;

public record UserInputDTO(
    String username,
    String password,
    @Email
    String email,
    int roleId
) {
} 