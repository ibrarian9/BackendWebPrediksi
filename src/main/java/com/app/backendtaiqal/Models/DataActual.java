package com.app.backendtaiqal.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "data_actual")
public class DataActual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String date;

    @Column(nullable = false, unique = true, length = 3)
    private int month;

    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal lajuProduksi;

}
