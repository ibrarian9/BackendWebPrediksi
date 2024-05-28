package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.Models.DataActual;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DataActualService {
    ResponseEntity<Map<String, Object>> getAllDataActual();
    ResponseEntity<Map<String, Object>> getDataActualById(Long id);
    ResponseEntity<Map<String, Object>> addDataActual(DataActual dataActual);
    ResponseEntity<Map<String, Object>> updateDataActual(Long id,DataActual dataActual);
    ResponseEntity<?> deleteDataActual(Long id);
}
