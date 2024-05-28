package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.Models.Month;
import com.app.backendtaiqal.Models.Production;
import com.app.backendtaiqal.Models.Rumus;
import com.app.backendtaiqal.Repository.MonthRepository;
import com.app.backendtaiqal.Repository.ProductionRepository;
import com.app.backendtaiqal.Repository.RumusRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ForecastServiceImpl implements ForecastService {

    private final ProductionRepository productionRepo;
    private final RumusRepository rumusRepo;
    private final MonthRepository monthRepo;

    @Override
    public ResponseEntity<Map<String, Object>> getAllProduct() {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", productionRepo.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getProductPage(int pageNumber, int pageSize) {
        if (pageNumber > 0) {
            pageNumber = pageNumber - 1;
        } else {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Production> productions = productionRepo.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("productions", productions.getContent());
        response.put("totalItems", productions.getTotalElements());
        response.put("totalPages", productions.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> addProduct(Production production) {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.CREATED.value());
        response.put("data", productionRepo.save(production));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> detailProduct(Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        Optional<Production> production = productionRepo.findById(id);
        production.ifPresent(value -> response.put("data", value));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editProduct(Long id, Production production) {
        Optional<Production> optionalProduction = productionRepo.findById(id);
        if (optionalProduction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> response = new HashMap<>();
        Production newProduction = optionalProduction.get();
        newProduction.setDate(production.getDate());
        newProduction.setProduction(production.getProduction());
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", productionRepo.save(newProduction));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        Optional<Production> optionalProduction = productionRepo.findById(id);
        if (optionalProduction.isPresent()) {
            productionRepo.delete(optionalProduction.get());

            Map<String, Object> response = new HashMap<>();
            response.put("httpStatus", HttpStatus.OK.value());
            response.put("message", "Delete Berhasil");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getRumus(Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        Optional<Rumus> rumus = rumusRepo.findById(id);
        rumus.ifPresent(value -> response.put("data", value));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editRumus(Long id, Rumus rumus) {
        Map<String, Object> response = new HashMap<>();
        Optional<Rumus> rumusOpt = rumusRepo.findById(id);
        if (rumusOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Rumus edRumus = rumusOpt.get();
        edRumus.setLamda(rumus.getLamda());
        edRumus.setLamda2(rumus.getLamda2());
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", rumusRepo.save(edRumus));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllMonth() {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", monthRepo.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> addMonth(Month month) {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.CREATED.value());
        response.put("data", monthRepo.save(month));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> editMonth(Long id, Month month) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteMonth(Long id) {
        Optional<Month> optionalMonth = monthRepo.findById(id);
        if (optionalMonth.isPresent()) {
            monthRepo.delete(optionalMonth.get());

            Map<String, Object> response = new HashMap<>();
            response.put("httpStatus", HttpStatus.OK.value());
            response.put("message", "Delete Berhasil");

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Month not found");
        }
    }
}
