package io.stocktrades.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import io.stocktrades.Constants;
import io.stocktrades.dto.request.AccessTokenRequestDto;
import io.stocktrades.util.ChecksumGenerator;
import io.stocktrades.util.HttpClient;

import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockService {

  private final HttpClient httpClient;

  private final RestTemplate restTemplate;

  @Value("zerodha.login.url")
  private String zerodhaLoginUrl;

  @Value("zerodha.base.ur")
  private String zerodhaBaseUrl;

  @Value("zerodha.access.url")
  private String zerodhaAccessUrl;

  @Value("dalaltrader.apiKey")
  private String apiKey;

  @Value("dalaltrader.apiSecret")
  private String apiSecret;

  public String getRequestToken() {
    log.info("String getRequestToken method Begins");

    String requestToken = restTemplate.getForEntity(zerodhaLoginUrl, String.class).getBody();
    log.info("the request token is:{}", requestToken);
    return requestToken;
  }

  public User getAccessToken(String requestToken,String userId) throws IOException, KiteException {
    log.info("String accessToken method Begins");

      KiteConnect kiteconnect = new KiteConnect(apiKey);
      kiteconnect.setUserId(userId);
      kiteconnect.getLoginURL();

    return kiteconnect.generateSession(requestToken, apiSecret);
  }
}
