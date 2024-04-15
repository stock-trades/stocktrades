package io.stocktrades.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Holding;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.User;
import io.stocktrades.dto.request.OrderDto;
import io.stocktrades.dto.response.LogResponseDto;
import io.stocktrades.service.StockService;
import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StockController {

  @Value("${zerodha.login.url}")
  private String zerodhaLoginUrl;

  @Value("${dalaltrader.apiKey}")
  private String apiKey;

  @Value("${zerodha.user.id}")
  private String userId;

  private final StockService stockService;

  @GetMapping("/redirect")
  public ResponseEntity<String> redirect(@RequestParam("request_token") String requestToken) {
    log.info("Redirect URL and requestParam is :{}", requestToken);
    return ResponseEntity.ok("Redirect API Call request token is:"+requestToken);
  }

  @GetMapping("/postback")
  public ResponseEntity<String> postbackOrderCompletionUpdates() {
    System.out.println("GET call Postback URL");
    return ResponseEntity.ok("Postback API Call ");
  }

  @GetMapping("/req_token")
  public ResponseEntity<String> getRequestToken() {
    log.info("zerodha Login url is:{} for apiKey:{}", zerodhaLoginUrl, apiKey);

    return ResponseEntity.ok(getRequestToken().getBody());
  }

  @GetMapping("/accessToken")
  public ResponseEntity<User> getAccessToken(@RequestParam("request_token") String requestToken)
      throws IOException, KiteException {
    return ResponseEntity.ok(stockService.getAccessToken(requestToken, userId));
  }

  @PostMapping("/placeOrder")
  public ResponseEntity<Order> placeEquityOrder(@RequestBody OrderDto orderDto)
      throws IOException, KiteException {
    return ResponseEntity.ok(stockService.postEquityOrder(orderDto, userId));
  }

  @GetMapping("/holdings")
  public ResponseEntity<List<Holding>> getEquityHoldings(/*@RequestParam("access_token")String accessToken*/) throws IOException, KiteException {
    return ResponseEntity.ok(stockService.getHoldings(userId/*,accessToken*/));
  }

  @PostMapping("/buy/{stock}/{price}")
  public ResponseEntity<Order> buyEquityStocks(@PathVariable("stock") String symbol,@PathVariable("price") String price)
      throws IOException, KiteException {
    return ResponseEntity.ok(stockService.buyStock(userId,symbol,price));
  }

  @PostMapping("/logout")
  public ResponseEntity<LogResponseDto> zerodhaLogout(@RequestParam("access_token") String accessToken)
          throws IOException, KiteException {
    return ResponseEntity.ok(stockService.logout(userId,apiKey,accessToken));
  }

}
