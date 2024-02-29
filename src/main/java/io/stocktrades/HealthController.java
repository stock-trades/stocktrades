package io.stocktrades;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
// testing endpoint
  @GetMapping("/health")
  public ResponseEntity<String> testTrades() {
    System.out.println("Varun ... StockTrades application is running");
    return ResponseEntity.ok("StockTrades application is running");
  }
}

 // Path: src/main/java/io/stocktrades/StockController.java
