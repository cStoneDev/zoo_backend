package org.app.zoo.auth.controller;

public record AuthRequest(
    String username,
    String password
){
}