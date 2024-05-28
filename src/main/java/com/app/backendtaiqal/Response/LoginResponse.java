package com.app.backendtaiqal.Response;

import com.app.backendtaiqal.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private long id;
    private String username;
    private String nama;
    private String email;
    private String role;
}
