package com.app.backendtaiqal.Repository;

import com.app.backendtaiqal.Models.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    Optional<Production> findById(Long id);
}
