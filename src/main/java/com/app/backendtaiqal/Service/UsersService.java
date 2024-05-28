package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.DTO.RegisterDTO;
import com.app.backendtaiqal.Models.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface UsersService {
    ResponseEntity<Map<String, Object>> getUsersPerPage(int pageNumber, int pageSize);
    ResponseEntity<Users> getUsersById(Long id);
    ResponseEntity<Map<String, Object>> getAllUsers();
    ResponseEntity<Users> addUsers(Users event);
    ResponseEntity<?> deleteUsersById(Long id);
    ResponseEntity<?> loginUser(Authentication authentication);
    void registerUser(RegisterDTO registerDTO);
}
