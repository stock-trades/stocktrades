package io.stocktrades.controller;

import com.zerodhatech.kiteconnect.KiteConnect;
import io.stocktrades.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StockController {

  @Value("${zerodha.login.url}")
  private  String zerodhaLoginUrl;

  private final StockService stockService;

  @GetMapping("/redirect")
  public ResponseEntity<String> redirect(@RequestParam("request_token") String requestToken) {
    log.info("Redirect URL and requestParam is :{}",requestToken);
    return ResponseEntity.ok("Redirect API Call ");
  }

  @GetMapping("/postback")
  public ResponseEntity<String> postbackOrderCompletionUpdates() {
    System.out.println("GET call Postback URL");
    return ResponseEntity.ok("Postback API Call ");
  }

  @GetMapping("/req_token")
  public ResponseEntity<String> getRequestToken() {
    log.info("zerodha Login url is:{}", zerodhaLoginUrl);
    KiteConnect kiteconnect = new KiteConnect();
    return ResponseEntity.ok(getRequestToken().getBody());
  }

  @GetMapping("/accessToken")
  public ResponseEntity<JSONObject> getAccessToken() {
    JSONObject accessToken = stockService.getAccessToken();
    return ResponseEntity.ok(accessToken);
  }
}
