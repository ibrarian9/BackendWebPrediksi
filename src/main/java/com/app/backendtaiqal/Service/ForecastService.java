package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.Models.Month;
import com.app.backendtaiqal.Models.Production;
import com.app.backendtaiqal.Models.Rumus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ForecastService {
    ResponseEntity<Map<String, Object>> getAllProduct();
    ResponseEntity<Map<String, Object>> getProductPage(int pageNumber, int pageSize);
     ResponseEntity<Map<String, Object>> addProduct(Production production);
     ResponseEntity<Map<String, Object>> detailProduct(Long id);
     ResponseEntity<Map<String, Object>> editProduct(Long id, Production production);
    ResponseEntity<?> deleteProduct(Long id);
    ResponseEntity<Map<String, Object>> getRumus(Long id);
    ResponseEntity<Map<String, Object>> editRumus(Long id, Rumus rumus);
    ResponseEntity<Map<String, Object>> getAllMonth();
    ResponseEntity<Map<String, Object>> addMonth(Month month);
    ResponseEntity<Map<String, Object>> editMonth(Long id, Month month);
    ResponseEntity<?> deleteMonth(Long id);
}
