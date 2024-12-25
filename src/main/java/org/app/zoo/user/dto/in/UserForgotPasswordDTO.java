// src/main/java/org/app/zoo/user/dto/in/UserForgotPasswordDTO.java
package org.app.zoo.user.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserForgotPasswordDTO(@Email @NotBlank String email) {
}