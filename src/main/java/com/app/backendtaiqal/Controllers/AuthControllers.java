package com.app.backendtaiqal.Controllers;

import com.app.backendtaiqal.DTO.RegisterDTO;
import com.app.backendtaiqal.Repository.UsersRepository;
import com.app.backendtaiqal.Response.ErrorResponse;
import com.app.backendtaiqal.DTO.LoginDTO;
import com.app.backendtaiqal.Service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, allowedHeaders = "/**")
@RestController
@RequestMapping("/auth")
public class AuthControllers {

    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    private final UsersRepository repository;

    @PostMapping(path = "/login")
    public ResponseEntity<?> Login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), loginDTO.getPassword()
            ));
            return usersService.loginUser(auth);
        } catch (BadCredentialsException e) {
            ErrorResponse errRes = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errRes);
        } catch (Exception e) {
            ErrorResponse errRes = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errRes);
        }
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> regiterUser(@RequestBody RegisterDTO registerDTO) {
        try {
            if (repository.existsByEmail(registerDTO.getEmail())) {
                return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.CONFLICT.value(), "Email already exists"));
            }
            usersService.registerUser(registerDTO);
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "User Registered successfully!"));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        try {
            return usersService.getAllUsers();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersPerPage(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "1") int pageSize
    ){
        try {
            return usersService.getUsersPerPage(pageNumber, pageSize);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping(path = "/hapus/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return usersService.deleteUsersById(id);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse errResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }
}
