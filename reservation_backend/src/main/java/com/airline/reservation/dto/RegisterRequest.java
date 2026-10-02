package com.airline.reservation.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private boolean registerAsAdmin;
    
    public boolean isRegisterAsAdmin() {
        return this.registerAsAdmin;
    }
}