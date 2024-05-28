package com.app.backendtaiqal.Controllers;

import com.app.backendtaiqal.Models.DataActual;
import com.app.backendtaiqal.Response.ErrorResponse;
import com.app.backendtaiqal.Service.DataActualService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, allowedHeaders = "/**")
@RestController
@RequestMapping("/dataActual")
public class DataActualControllers {

    private final DataActualService dataActualService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        try {
            return dataActualService.getAllDataActual();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody DataActual dataActual) {
        try {
            return dataActualService.addDataActual(dataActual);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping(path = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody DataActual dataActual) {
        try {
            return dataActualService.updateDataActual(id, dataActual);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return dataActualService.getDataActualById(id);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return dataActualService.deleteDataActual(id);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse errResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }
}
