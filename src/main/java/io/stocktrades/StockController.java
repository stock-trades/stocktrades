package io.stocktrades;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @GetMapping("/redirect")
    public ResponseEntity<String> redirect() {
        System.out.println("Redirect URL");
        return ResponseEntity.ok("Redirect API Call ");
    }

    @GetMapping("/postback")
    public ResponseEntity<String> postbackOrderCompletionUpdates() {
        System.out.println("GET call Postback URL");
        return ResponseEntity.ok("Postback API Call ");
    }


}
