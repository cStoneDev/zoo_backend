package org.app.zoo.user.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserResetPasswordDTO(@NotBlank String token, @NotBlank @Min(4) String password) {

}
