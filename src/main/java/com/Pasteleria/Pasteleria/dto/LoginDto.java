package com.Pasteleria.Pasteleria.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username; // Cambiado de email a username
    private String password;
}