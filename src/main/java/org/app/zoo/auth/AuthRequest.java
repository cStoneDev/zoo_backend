package org.app.zoo.auth;

public class AuthRequest {
    private String username;
    
    private String password;

    // Constructor sin argumentos
    public AuthRequest() {}

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


