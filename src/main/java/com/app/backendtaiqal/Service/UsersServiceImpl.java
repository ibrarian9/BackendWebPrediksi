package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.DTO.RegisterDTO;
import com.app.backendtaiqal.Models.Users;
import com.app.backendtaiqal.Repository.RoleRepository;
import com.app.backendtaiqal.Repository.UsersRepository;
import com.app.backendtaiqal.Response.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repo;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Map<String, Object>> getUsersPerPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Users> users = repo.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", users.getContent());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Users> getUsersById(Long id) {
        Users user = repo.findById(id).orElse(null);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", repo.findAll());
        return ResponseEntity.ok(response);
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

    @Override
    public ResponseEntity<?> loginUser(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new LoginResponse(
                jwt,
                userDetails.getUsers().getId(),
                userDetails.getUsername(),
                userDetails.getUsers().getNama(),
                userDetails.getUsers().getEmail(),
                roles.getFirst()
        ));
    }

    @Transactional
    public void registerUser(RegisterDTO registerDTO) {
        Users user = new Users(
                registerDTO.getNama(),
                registerDTO.getUsername(),
                registerDTO.getEmail(),
                passwordEncoder.encode(registerDTO.getPassword()),
                roleRepository.findByName(registerDTO.getRoles())
                );
        repo.save(user);
    }
}
