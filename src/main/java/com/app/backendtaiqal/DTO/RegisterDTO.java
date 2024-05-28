package com.app.backendtaiqal.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterDTO {
    private String username;
    private String nama;
    private String email;
    private String password;
    private String roles;
}
