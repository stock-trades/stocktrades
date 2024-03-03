package io.stocktrades.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import io.stocktrades.util.HttpClient;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockService {

  private final HttpClient httpClient;

  private final RestTemplate restTemplate;

  @Value("${zerodha.login.url}")
  private String zerodhaLoginUrl;

  @Value("${dalaltrader.apiKey}")
  private String apiKey;

  @Value("${dalaltrader.apiSecret}")
  private String apiSecret;

  public String getRequestToken() {
    log.info("String getRequestToken method Begins");

    String requestToken = restTemplate.getForEntity(zerodhaLoginUrl, String.class).getBody();
    log.info("the request token is:{}", requestToken);
    return requestToken;
  }

  public User getAccessToken(String requestToken, String userId) throws IOException, KiteException {
    log.info("String accessToken method Begins for reToken:{},userId:{}", requestToken, userId);

    KiteConnect kiteconnect = new KiteConnect(apiKey);
    kiteconnect.setUserId(userId);
    String loginURL = kiteconnect.getLoginURL();

    log.info("the loginUrl is:{}",loginURL);
    User user = kiteconnect.generateSession(requestToken, apiSecret);
    log.info("the User  is:{}",user.toString());

    return user;
  }
}
