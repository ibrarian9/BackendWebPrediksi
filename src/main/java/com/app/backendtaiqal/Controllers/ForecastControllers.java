package com.app.backendtaiqal.Controllers;

import com.app.backendtaiqal.Models.Month;
import com.app.backendtaiqal.Models.Production;
import com.app.backendtaiqal.Models.Rumus;
import com.app.backendtaiqal.Response.ErrorResponse;
import com.app.backendtaiqal.Service.ForecastService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"}, allowedHeaders = "/**")
@RestController
@RequestMapping("/forecast")
public class ForecastControllers {

    private final ForecastService forecastService;

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllProducts() {
        try {
            return forecastService.getAllProduct();
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getProductPage(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", defaultValue = "1") int pageLimit
    ){
        return forecastService.getProductPage(pageNumber, pageLimit);
    }

    @GetMapping(path = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        try {
            return forecastService.detailProduct(id);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping(path = "/product/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct(@RequestBody Production production) {
        try {
            return forecastService.addProduct(production);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping(path = "/product/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editProduct(@PathVariable("id") Long id, @RequestBody Production production) {
        try {
            return forecastService.editProduct(id, production);
        } catch (Exception e){
            return handleException(e);
        }
    }

    @DeleteMapping(path = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        try {
            return forecastService.deleteProduct(id);
        } catch (Exception e){
            return handleException(e);
        }
    }

    @GetMapping(path = "/rumus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRumus(@PathVariable Long id) {
        try {
            return forecastService.getRumus(id);
        } catch (Exception e){
            return handleException(e);
        }
    }

    @PutMapping(path = "/rumus/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRumus(@PathVariable Long id, @RequestBody Rumus rumus) {
        try {
            return forecastService.editRumus(id, rumus);
        } catch (Exception e){
            return handleException(e);
        }
    }

    @GetMapping(path = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMonth() {
        try {
            return forecastService.getAllMonth();
        } catch (Exception e){
            return handleException(e);
        }
    }

    @PostMapping(path = "/month/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMonth(@RequestBody Month month) {
        try {
            return forecastService.addMonth(month);
        } catch (Exception e){
            return handleException(e);
        }
    }

    @PutMapping(path = "/month/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editMonth(@PathVariable Long id, @RequestBody Month month) {
        try {
            return forecastService.editMonth(id, month);
        } catch (Exception e){
            return handleException(e);
        }
    }

    @DeleteMapping(path = "/month/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteMonth(@PathVariable Long id) {
        try {
            return forecastService.deleteMonth(id);
        } catch (Exception e){
            return handleException(e);
        }
    }

    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse errResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }
}
