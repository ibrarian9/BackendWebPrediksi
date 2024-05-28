package com.app.backendtaiqal.Service;

import com.app.backendtaiqal.Models.DataActual;
import com.app.backendtaiqal.Repository.DataActualRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DataActualServiceImpl implements DataActualService {

    private final DataActualRepository actualRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllDataActual() {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", actualRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDataActualById(Long id) {
        Optional<DataActual> actual = actualRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.OK.value());
        actual.ifPresent(dataActual -> response.put("data", dataActual));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> addDataActual(DataActual dataActual) {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", HttpStatus.CREATED.value());
        response.put("data", actualRepository.save(dataActual));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateDataActual(Long id, DataActual dataActual) {
        Map<String, Object> response = new HashMap<>();
        Optional<DataActual> actual = actualRepository.findById(id);
        if (actual.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        DataActual existing = actual.get();
        existing.setDate(dataActual.getDate());
        existing.setMonth(dataActual.getMonth());
        existing.setLajuProduksi(dataActual.getLajuProduksi());
        response.put("httpStatus", HttpStatus.OK.value());
        response.put("data", actualRepository.save(existing));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteDataActual(Long id) {
        Optional<DataActual> actual = actualRepository.findById(id);
        if (actual.isPresent()){
            actualRepository.delete(actual.get());
            Map<String, Object> response = new HashMap<>();
            response.put("httpStatus", HttpStatus.OK.value());
            response.put("message", "Data actual has been deleted");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
