package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.Models.Users;
import com.app.backendtaiqal.Repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repo;

    @Override
    public ResponseEntity<List<Users>> getUsersPerPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Users> users = repo.findAll(pageable);
        return ResponseEntity.ok(users.getContent());
    }

    @Override
    public ResponseEntity<Users> getUsersById(Long id) {
        Users user = repo.findById(id).orElse(null);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(repo.findAll());
    }

    @Override
    public ResponseEntity<Users> addUsers(Users event) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteUsersById(Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
