package io.stocktrades;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> testTrades(){
        return ResponseEntity.ok("StockTrades application is running");
    }
}
 // Path: src/main/java/io/stocktrades/StockController.java