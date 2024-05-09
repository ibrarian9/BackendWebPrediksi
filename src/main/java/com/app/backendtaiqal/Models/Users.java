package com.app.backendtaiqal.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nama", length = 50, nullable = false)
    private String nama;
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;
    @Column(name = "password", length = 120, nullable = false)
    private String password;

    public Users(String nama, String username, String email, String password) {
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
