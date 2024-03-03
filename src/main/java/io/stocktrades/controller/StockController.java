package io.stocktrades.controller;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import io.stocktrades.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StockController {

  @Value("${zerodha.login.url}")
  private  String zerodhaLoginUrl;

  @Value("dalaltrader.apiKey")
  private String apiKey;

  @Value("zerodha.user.id")
  private String userId;

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
    log.info("zerodha Login url is:{} for apiKey:{}", zerodhaLoginUrl,apiKey);

    return ResponseEntity.ok(getRequestToken().getBody());
  }

  @GetMapping("/accessToken")
  public ResponseEntity<User> getAccessToken(@RequestParam("request_token") String requestToken) throws IOException, KiteException {
    return ResponseEntity.ok(stockService.getAccessToken(requestToken,userId));
  }



}
