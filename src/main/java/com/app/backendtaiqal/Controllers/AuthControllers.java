package com.app.backendtaiqal.Controllers;

import com.app.backendtaiqal.DTO.RegisterDTO;
import com.app.backendtaiqal.Models.Users;
import com.app.backendtaiqal.Repository.UsersRepository;
import com.app.backendtaiqal.Response.ErrorResponse;
import com.app.backendtaiqal.DTO.LoginDTO;
import com.app.backendtaiqal.Response.LoginResponse;
import com.app.backendtaiqal.Service.JwtService;
import com.app.backendtaiqal.Service.UserDetailsImpl;
import com.app.backendtaiqal.Service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, allowedHeaders = "/**")
@RestController
@RequestMapping("/auth")
public class AuthControllers {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsersService usersService;
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(path = "/login")
    public ResponseEntity<?> Login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), loginDTO.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtService.generateJwtToken(auth);
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

            return ResponseEntity.ok(new LoginResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getName(),
                    userDetails.getEmail()
            ));
        } catch (BadCredentialsException e) {
            ErrorResponse errRes = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errRes);
        } catch (Exception e) {
            ErrorResponse errRes = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errRes);
        }
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> regiterUser(@RequestBody RegisterDTO registerDTO) {
        try {
            if (repository.existsByEmail(registerDTO.getEmail())) {
                return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.CONFLICT, "Email already exists"));
            }

            Users users = new Users(
                    registerDTO.getNama(),
                    registerDTO.getUsername(),
                    registerDTO.getEmail(),
                    passwordEncoder.encode(registerDTO.getPassword()));

            repository.save(users);

            return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK, "User Registered successfully!"));
        } catch (Exception e) {
            ErrorResponse errRes = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errRes);
        }
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersPerPage(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "1") int pageSize
    ){
        return usersService.getUsersPerPage(pageNumber, pageSize);
    }

    @DeleteMapping(path = "/hapus/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return usersService.deleteUsersById(id);
    }
}
