package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.Models.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {
    ResponseEntity<List<Users>> getUsersPerPage(int pageNumber, int pageSize);
    ResponseEntity<Users> getUsersById(Long id);
    ResponseEntity<List<Users>> getAllUsers();
    ResponseEntity<Users> addUsers(Users event);
    ResponseEntity<?> deleteUsersById(Long id);
}
