package org.app.zoo.config.errorHandling;

public record ErrorResponse(
    int status,
    String message
) {
    
}
