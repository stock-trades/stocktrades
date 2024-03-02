package io.stocktrades.controller;

import io.stocktrades.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<String> redirect() {
    System.out.println("Redirect URL");
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
    UriComponents loginUrl = UriComponentsBuilder.fromHttpUrl(zerodhaLoginUrl).build();
    System.out.println("the url is:" + loginUrl);
    return ResponseEntity.ok("Postback API Call ");
  }

  @GetMapping("/accessToken")
  public ResponseEntity<JSONObject> getAccessToken() {
    JSONObject accessToken = stockService.getAccessToken();
    return ResponseEntity.ok(accessToken);
  }
}
